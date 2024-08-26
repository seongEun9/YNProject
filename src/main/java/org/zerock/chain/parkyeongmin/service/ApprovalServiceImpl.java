package org.zerock.chain.parkyeongmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.chain.parkyeongmin.dto.DocumentsDTO;
import org.zerock.chain.parkyeongmin.model.Approval;
import org.zerock.chain.parkyeongmin.model.Documents;
import org.zerock.chain.parkyeongmin.model.Employee;
import org.zerock.chain.parkyeongmin.repository.ApprovalRepository;
import org.zerock.chain.parkyeongmin.repository.DocumentsRepository;
import org.zerock.chain.parkyeongmin.repository.EmployeesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final DocumentsRepository documentsRepository;
    private final EmployeesRepository employeesRepository;

    // approvals테이블에 사용자가 정한 정보가 저장되는 메서드
    @Override
    public void requestApproval(DocumentsDTO documentsDTO) {
        // approverJson을 파싱하여 List<Map<String, Object>>로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> approvers;

        try {
            approvers = objectMapper.readValue(documentsDTO.getApproversJson(), new TypeReference<>() {});
            log.info("Parsed approvers: {}", approvers);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing approverJson", e);
        }

        if (approvers.isEmpty()) {
            log.warn("Approval line is empty. Throwing exception.");
            throw new RuntimeException("Approval line is empty. Please set up the approval line.");
        }

        // 결재선의 결재자들을 저장 (DocStatus는 변경하지 않음)
        for (Map<String, Object> approver : approvers) {
            // 문서와 사원 객체를 조회하여 설정
            Documents document = documentsRepository.findById(documentsDTO.getDocNo())
                    .orElseThrow(() -> new RuntimeException("Document not found"));
            log.info("Found document: {}", document);

            // 문자열로 들어온 값을 Long과 Integer로 변환
            Long empNo = Long.valueOf(approver.get("empNo").toString());
            Integer approvalOrder = Integer.valueOf(approver.get("approvalOrder").toString());

            log.info("Processing approval for empNo: {}, approvalOrder: {}", empNo, approvalOrder);

            Employee employee = employeesRepository.findById(empNo)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            log.info("Found employee: {}", employee);

            // Approval 객체 생성 및 설정
            Approval approval = new Approval();
            approval.setDocuments(document);    // 문서 객체 설정
            approval.setEmployee(employee);     // 사원 객체 설정
            approval.setApprovalOrder(approvalOrder);
            approval.setApprovalStatus("대기");  // 초기 상태는 대기
            approvalRepository.save(approval);
            log.info("Saved approval: {}", approval);
        }

        // 첫 번째 결재자에게 문서 할당
        Map<String, Object> firstApprover = approvers.get(0);
        // String 타입을 Long으로 바꾸기 위한 코드
        String empNoStr = (String) firstApprover.get("empNo");
        Long firstEmpNo = Long.parseLong(empNoStr);
        assignDocumentToApprover(documentsDTO.getDocNo(), firstEmpNo);
        log.info("Assigned document {} to first approver with empNo: {}", documentsDTO.getDocNo(), firstEmpNo);
    }

    @Override  // 결재자가 승인하면 그 승인이 작동되는 메서드
    public void approveDocument(int docNo, Long empNo) {
        // 문서 조회
        Documents document = documentsRepository.findById(docNo)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        Approval approval = approvalRepository.findByDocumentsDocNoAndEmployeeEmpNo(docNo, empNo);

        if (approval != null) {
            approval.setApprovalDate(LocalDateTime.now());
            approval.setApprovalStatus("승인");  // 승인 상태로 변경
            approvalRepository.save(approval);
        }

        // 다음 결재자를 찾기 전에 현재 결재가 최종 결재인지 확인
        List<Approval> approvals = approvalRepository.findByDocumentsDocNo(docNo);
        boolean allApproved = approvals.stream()
                .allMatch(a -> "승인".equals(a.getApprovalStatus()));

        if (allApproved) {
            // 모든 결재가 승인되었으므로 문서를 완료로 설정
            finalizeDocument(docNo);
        } else {
            // 최종 결재가 아니므로 다음 결재자로 이동
            moveToNextApprover(docNo, approval.getApprovalOrder() + 1);
        }
    }

    @Override
    public void rejectDocument(int docNo, Long empNo, String rejectionReason) {
        Approval approval = approvalRepository.findByDocumentsDocNoAndEmployeeEmpNo(docNo, empNo);

        if (approval != null) {
            approval.setApprovalStatus("반려");  // 반려 상태로 변경
            approval.setRejectionReason(rejectionReason);
            approvalRepository.save(approval);

            // 문서의 상태도 반려로 변경
            Documents document = documentsRepository.findById(docNo)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
            document.setDocStatus("반려");
            documentsRepository.save(document);

            // 반려 처리 (작성자에게 알림 등)
            handleRejection(docNo);
        }
    }

    @Override       // 다음 결재자가 있다면 해야될 기능 처리
    public void moveToNextApprover(int docNo, int nextOrder) {
        Approval nextApproval = approvalRepository.findByDocumentsDocNoAndApprovalOrder(docNo, nextOrder);

        if (nextApproval != null) {
            // 다음 결재자가 있으면, 문서를 다음 결재자에게 할당하고, DocStatus를 "진행중"으로 변경
            assignDocumentToApprover(nextApproval.getDocuments().getDocNo(), nextApproval.getEmployee().getEmpNo());

            Documents document = documentsRepository.findById(docNo)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
            document.setDocStatus("진행 중");
            documentsRepository.save(document);
        } else {
            // 다음 결재자가 없으면, 문서를 최종 완료 상태로 변경
            finalizeDocument(docNo);
        }
    }

    @Override // 마지막 결재자에게 문서가 넘어가면 완료 처리
    public void finalizeDocument(int docNo) {
        Documents document = documentsRepository.findById(docNo)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setDocStatus("완료");
        documentsRepository.save(document);

        System.out.println("Document No: " + docNo + " has been finalized.");
    }

    // 문서를 첫 번째 결재자에게 할당하는 메서드
    private void assignDocumentToApprover(int docNo, Long empNo) {
        // 문서를 조회
        Documents document = documentsRepository.findById(docNo)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // 첫 번째 결재자를 조회 (approvalOrder가 1인 결재자)
        Approval firstApproval = approvalRepository.findByDocumentsDocNoAndApprovalOrder(docNo, 1);

        if (firstApproval == null) {
            throw new RuntimeException("Approval not found for the given document and approver.");
        }

        // Approval 객체에 필요한 로직 추가 (예: 문서 할당 상태 변경 등)
        // ...

        // 문서를 첫 번째 결재자에게 할당했다고 로그 출력
        System.out.println("Document No: " + docNo + " has been assigned to the first approver with empNo: " + empNo);
    }

    // 반려 처리 로직 (임의로 추가)
    private void handleRejection(int docNo) {
        // 문서 반려 시 처리할 로직을 구현합니다.
        // 예를 들어, 작성자에게 알림을 보내거나, 문서 상태를 변경하는 등의 작업을 할 수 있습니다.
        System.out.println("Document No: " + docNo + " has been rejected.");
    }

    @Override
    public boolean isFirstApprovalApproved(int docNo) {
        // 첫 번째 결재자의 상태를 조회
        Approval firstApproval = approvalRepository.findByDocumentsDocNoAndApprovalOrder(docNo, 1);

        if (firstApproval != null) {
            String approvalStatus = firstApproval.getApprovalStatus();
            log.info("First approval status for docNo {}: {}", docNo, approvalStatus); // 로그 추가

            // 첫 번째 결재자가 승인한 경우 true 반환
            return "승인".equals(approvalStatus);
        }

        log.info("No first approval found for docNo {}", docNo); // 로그 추가
        // 결재 정보가 없거나 승인이 아닌 경우 false 반환
        return false;
    }

    @Override
    public boolean isCurrentApprover(int docNo, Long empNo) {
        // 현재 사용자의 Approval 정보 조회
        Approval currentApproval = approvalRepository.findByDocumentsDocNoAndEmployeeEmpNo(docNo, empNo);

        if (currentApproval != null) {
            int currentOrder = currentApproval.getApprovalOrder();

            // 이전 결재자의 승인 여부 확인
            if (currentOrder > 1) {
                Approval previousApproval = approvalRepository.findByDocumentsDocNoAndApprovalOrder(docNo, currentOrder - 1);

                // 이전 결재자가 승인 또는 반려했을 때만 현재 결재자가 승인/반려 버튼을 볼 수 있음
                if (previousApproval != null && ("승인".equals(previousApproval.getApprovalStatus()))) {
                    return "대기".equals(currentApproval.getApprovalStatus());
                } else {
                    return false;
                }
            } else {
                // 첫 번째 결재자라면 이전 결재자가 없으므로 바로 승인/반려 가능
                return "대기".equals(currentApproval.getApprovalStatus());
            }
        }
        return false;
    }

    @Override
    public boolean isDocumentApprover(int docNo, Long empNo) {
        // 해당 문서의 결재자 목록에서 현재 사용자가 포함되어 있는지 확인
        Approval approval = approvalRepository.findByDocumentsDocNoAndEmployeeEmpNo(docNo, empNo);
        return approval != null;
    }

    @Override  // 결재자의 받은 문서함에 대기상태 문서개수 조회
    public int countPendingApprovals(Long empNo) {
        return approvalRepository.countPendingApprovalsByEmpNo(empNo);
    }

    @Override  // 결재자의 받은 문서함에 승인상태 문서개수 조회
    public int countApprovedApprovals(Long empNo) {
        return approvalRepository.countApprovedApprovalsByEmpNo(empNo);
    }

    @Override  // 결재자의 받은 문서함에 반려상태 문서개수 조회
    public int countRejectedDocumentsForApprover(Long empNo) {
        return approvalRepository.countRejectedDocumentsForApprover(empNo);
    }

}
