<!-- Heading -->

# CHAIN

> Spring BootとSpring MVCパターンをベースにSpring Securityを活用して施作したグループウェアプロジェクトCHAINです。<br>
> 👉 Click [here](http://docs.yi.or.kr:8091/)

### ✔️ 開発動機及び概要
- 様々な業務で使用できるように流動的な業務ツールを制作しました。
-	管理者と社員ページに分けて体系的な実装を目指しました。
-	チームの効率的なコラボレーションと生産性向上のための総合的なツールを提供しました。
-	人事管理を効率的に監督し、システム管理ツールを提供しました。
-	定期間内に高クオリティのWebサイトを実現するためにBootstrapを使用しました。


### ✔️ 期間
  `2024.06.13 ~ 2024.09.05`

***

# 開発環境
<table>
  <thead>
    <tr>
      <th style="text-align: center;">分類</th>
      <th style="text-align: center;">内容</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>FRAMEWORK</td>
      <td><img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" style="border-radius:10px"> <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" style="border-radius:10px"> </td>
    </tr>
    <tr>
      <td>LIBRARY</td>
      <td><img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" style="border-radius:10px"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white" style="border-radius:10px"> <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white" style="border-radius:10px"> </td>
    </tr>
     <tr>
      <td>LANGUAGE</td>
      <td><img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white" style="border-radius:10px"> <img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white" style="border-radius:10px"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" style="border-radius:10px">  <img src="https://img.shields.io/badge/java-DE8E2f?style=for-the-badge" style="border-radius:10px"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white" style="border-radius:10px"></td>
    </tr>
     <tr>
      <td>VCS</td>
      <td><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white" style="border-radius:10px"> <img src="https://img.shields.io/badge/sourcetree-0052cc?style=for-the-badge&logo=sourcetree&logoColor=white" style="border-radius:10px"></td>
    </tr>
     <tr>
      <td>IDE</td>
      <td><img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white" style="border-radius:10px"> </td>
    </tr>
  </tbody>
</table>

***
# ページ構成
### 🧑‍💼 社員ページ
![社員1](https://github.com/user-attachments/assets/2aa74abe-c571-48d8-94f7-85cfb44092cc)
![社員2](https://github.com/user-attachments/assets/904bb910-056d-4f49-a68b-8617141ab909)
![社員3](https://github.com/user-attachments/assets/1326bea3-9534-4a19-a7e5-8e5a002d3ee1)

### 🛠️ 管理者ページ
![管理者](https://github.com/user-attachments/assets/3eaa7e64-d6a3-4460-b702-b31c3a328d85)

***
# DataBase ERD
![chain_ERD](https://github.com/user-attachments/assets/7dda70c3-6811-4de7-9d62-275382e88a5b)

***
# 機能紹介
## 🧑‍💼 社員ページ
### ✔️ アカウントを作成
![アカウントを作成](https://github.com/user-attachments/assets/96ac0983-ff70-4b97-a9f3-f61d99fd3589)
- 全ての内容を入力しないとアカウントを作成ができません。
- 電話番号、電子メールの重複値は許可されません。
- アカウントを作成が完了すると、社員番号を通知でお知らせします。
  
### ✔️ メインページ
![メインウィジェットセッティング](https://github.com/user-attachments/assets/5ed10867-d5c9-462b-b1f6-8fea77188e85)
- 上部バーとサイドバー、メッセンジャーアイコンを固定し、すべてのページからアクセスできます。
- ウィジェット編集をクリックして、Drag and Dropでウィジェットの順序を変更できます。

### ✔️ メッセンジャー
![メッセンジャー](https://github.com/user-attachments/assets/96446aa1-0ffd-4a08-8148-c6d214dee1ae)
- WebSocketを活用して1:1リアルタイムメッセンジャーを具現しました。
- 組織図及びチャットルームリストの照会、チャットルームの作成及び出ることが可能です。
- メインのメッセンジャーアイコンとメッセンジャーのチャットリストで、読んでいないチャット数を確認することができます。
- チャットルームでメッセージの読み取り状態を確認することができます。

### ✔️ 通知
![通知](https://github.com/user-attachments/assets/7f11edda-ea03-4a0b-9b3d-f14a5ef2ac72)
- トリガーとイベントを使用して社員固有のカスタマイズ通知を実装しました。
- 上段バーアイコンから未読の通知の数が最新順に表示されます。
- 各通知をクリックすると、そのページに移動できます。

![通知全体の削除部分](https://github.com/user-attachments/assets/48753450-5d5c-4c7e-a7cd-969a6f41deb0)
- カテゴリ別の照会と読み取りの削除、全体の削除が可能です。
- 既読の通知は上段バーから除外され、全ページでもスタイルが変更されて表示されます。
- 通知生成時間の経過と共に異なって表示されます。
- 管理者は４つのカテゴリでシステム通知を登録できます。

### ✔️ システム設定
![システム設定](https://github.com/user-attachments/assets/39260530-1f9f-411c-8cb5-82856b0ad9ef)
- 個人情報を修正することができます。
- カテゴリ別の通知設定の変更(on/off)が可能です。

### ✔️ マイページ
![マイペース](https://github.com/user-attachments/assets/1e4bbf97-38aa-49fe-9096-7c367f3553fb)
- 出退勤及び勤怠記録を確認することができます。

### ✔️ TODO
![TODO](https://github.com/user-attachments/assets/e71d4f97-ab06-4458-a4db-3bd9522c6af3)
- TODO照会、登録、削除が可能です。
- 登録時に、お気に入りとカテゴリを設定できます。
- 「お気に入り、Today、全体表示、進行項目、完了項目」に分類してTODOを管理することができます。
- 完了したTODOは削除することができます。

### ✔️ プロジェクト
![プロジェクト](https://github.com/user-attachments/assets/905dacfb-ccf5-4e90-a4c4-56aac2bdffcc)
- プロジェクトの照会、登録、修正、削除が可能です。
- プロジェクト登録
  - プロジェクト作成者はログインした社員として自動保存されます。
  - プロジェクト参加者の選択と期間指定、５段階の進行度を作成することができます。
  - 進行度をチェックすることで、チャートページでプロジェクトの進行度を確認することができます。
- プロジェクトの一時保存及びお気に入りすることができます。
- お気に入りのプロジェクトは一番前に表示されます。

### ✔️ 業務
![業務](https://github.com/user-attachments/assets/c1ff8a27-cb59-4b25-9b25-c76a430b7f86)
- 業務文書の照会、登録、修正、削除が可能で、作成者のみ修正できます。
- 業務登録
  - 文書様式によって「会議録、業務報告書、業務日誌」の３種類の文書を作成することができます。
  - 作成者はログインした社員として自動保存されます。
  - 参加者を選択及び期間を指定することができます。
  - 作成中の文書は一時保存ができます。

### ✔️ 차트
![차트-프로젝트](https://github.com/user-attachments/assets/650a7018-e1d0-431e-9597-b4d322cd6871)
- 프로젝트 차트
  - 사원별 프로젝트 진행도 통계 조회가 가능합니다.
  - 연도별, 월별 진행도 차트를 조회할 수 있습니다.

![차트-okr](https://github.com/user-attachments/assets/3fdc3930-15ff-4bcf-91b7-37020636c0c7)
- OKR 차트
  - 사원별 목표 진행도 통계 조회가 가능합니다.
  - 목표는 조회, 등록, 수정, 삭제할 수 있습니다.
  - 연도별, 월별, 카테고리별 차트를 조회할 수 있으며, 목표별로 차트를 비교할 수 있습니다.

### ✔️ 전자결재
![전자결재 상태](https://github.com/user-attachments/assets/5335a887-cb48-4186-ba14-c23acda7a535)
- 결재 문서 조회, 등록, 수정, 삭제가 가능합니다.
- 상단 카드를 클릭하여 상태별로 조회가 가능합니다.

![전자결재 요청](https://github.com/user-attachments/assets/90c7f33b-d25a-4e8e-a751-84ca8e6e3400)
- 결재 요청 시, 결재자와 참조자를 선택할 수 있고, 결재자는 필수 선택입니다.
- 요청한 문서는 승인되기 전에 철회가 가능합니다.

![전자결재 1차 승인](https://github.com/user-attachments/assets/79751b50-6d21-4565-84e2-92f24e162b2f)
![전자결재 2차 반려](https://github.com/user-attachments/assets/fbefc112-7098-45bb-8314-033b73904df0)
- 결재자는 요청 받은 문서를 승인 또는 반려할 수 있습니다.
- 승인된 문서는 다음 결재자에게 넘어가고, 최종 결재자까지 승인을 받으면 결재가 완료됩니다.
- 반려한 문서는 반려 사유를 작성할 수 있고, 반려 받은 문서는 재작성이 가능합니다.

![전자결재 임시보관함](https://github.com/user-attachments/assets/e1d96a63-3787-4d49-a363-a29a18dc3e43)
- 작성 중인 문서는 임시 저장을 할 수 있고, 임시 저장된 문서는 수정, 요청, 삭제가 가능합니다.

### ✔️ 이메일
![email OAuth 인증](https://github.com/user-attachments/assets/6059b002-2c6b-4a69-aa48-0231989c3ccb)
- Gmail API를 활용하여 웹 메일을 구현했습니다.

![이메일](https://github.com/user-attachments/assets/c42a90d5-a6ff-4c65-a10a-bb94cb7edd3f)
- 메일 작성 시, 여러 명의 수신자를 선택할 수 있고, 메일 주소를 검증하여 올바른 주소인지 확인합니다.
- 메일 조회, 송수신, 전달, 답장, 임시저장, 즐겨찾기, 메일함 이동, 삭제가 가능합니다.
- 아이콘 및 상태를 활용해 읽은 메일과 읽지 않은 메일을 한 눈에 파악할 수 있습니다.
- 목록에서 전체 선택 및 선택 해제, 별표 아님만 선택이 가능합니다.
- 즐겨찾기, 중요 메일, 내게 쓴 메일, 임시 보관을 별도로 관리할 수 있습니다.
- 휴지통에서 개별 및 전체 삭제, 복구가 가능합니다.

### ✔️ 공지사항
![공지사항](https://github.com/user-attachments/assets/d30b594e-d257-4753-90c8-3d4cfecaafa2)
- 일반 사원은 조회만 가능하고, 권한을 가진 사원만 등록, 수정, 삭제가 가능합니다.
- 공지사항 작성
  - 작성자는 로그인한 사원으로 자동 저장됩니다.
  - 날짜를 지정하여 게시글 상단에 고정할 수 있습니다.

### ✔️ 경조사
![경조사](https://github.com/user-attachments/assets/a9344343-dff3-42fd-b7a3-1835fb9b0709)
- 일반 사원은 조회, 등록만 가능하고, 권한을 가진 사원만 수정, 삭제가 가능합니다.
- 게시글 작성
  - 작성자는 로그인한 사원으로 자동 저장됩니다.
  - '결혼식, 장례식, 기타' 3개의 카테고리 중 선택해 작성할 수 있습니다.
  - 카카오 지도 API를 사용해 장소를 지정할 수 있습니다.

### ✔️ 구내식당
- 일반 사원은 조회만 가능하고, 권한을 가진 사원이 이미지 파일을 업로드하여 식단을 등록, 삭제를 할 수 있습니다.

### ✔️ 고객센터
![고객센터](https://github.com/user-attachments/assets/a9e8c1e8-4553-417c-a8f7-89dc3477ac3d)
- 일반 사원
  - 자주 묻는 질문 조회 및 문의글 작성, 조회가 가능합니다.
  - 문의글 작성 시, 작성자는 로그인한 사원으로 자동 저장됩니다.
  - 관리자의 답변이 오기 전까지 문의글을 수정 및 삭제할 수 있습니다.
- 관리자
  - 자주 묻는 질문(FAQ)을 등록, 수정, 삭제할 수 있습니다.
  - 문의글 답변 등록, 수정, 삭제할 수 있습니다.

## 🛠️ 管理者ページ
![管理者ページに移動](https://github.com/user-attachments/assets/5e1b0dd9-9715-46a0-ade2-a59959e53fae)
- 관리자 권한을 가진 사원은 사원 페이지에서 관리자 페이지로 접속할 수 있습니다.

### ✔️ 사원 관리
![관리자 사원관리](https://github.com/user-attachments/assets/ce3e12d6-7b22-47c4-8b11-56f068f84910)
- 사원을 조회, 등록, 수정, 삭제가 가능합니다.
- 사원 등록으로 신규 사원을 추가할 수 있습니다.
- 사원의 부서 및 직급 등 세부 정보 수정이 가능합니다.
- 부서 및 직급 변경 시, 해당 사원은 알림으로 변경 사항을 확인할 수 있습니다.

### ✔️ 권한 관리
![관리자 권한관리](https://github.com/user-attachments/assets/01a39ffa-4242-4cbf-a68a-4aa50cee5016)
- 부서 등록, 수정, 삭제가 가능합니다.
- 부서별로 사원을 조회하여, 개별 권한을 부여할 수 있습니다.

![관리자 권한](https://github.com/user-attachments/assets/b6accadf-04e5-4273-9aff-95325a934e42)
- 개별 권한은 '관리자, 공지사항, 경조사, 구내식당'이 있습니다.
- 관리자 권한으로 시스템 알림 및 고객센터 질의답변을 할 수 있습니다.

### ✔️ 근태 관리
![관리자 근태관리](https://github.com/user-attachments/assets/23c2c839-cb4f-4e8f-bf3c-946a591772be)
- 월 종합 근태를 조회할 수 있습니다.
- 특정 사원 이름을 선택하면 월별로 나뉜 일일 근태를 조회, 수정, 삭제할 수 있습니다.

### ✔️ 연차 관리
![관리자 연차관리](https://github.com/user-attachments/assets/f5c2df0d-a92e-49cc-87bc-23dad186e7de)
- 사원별 연차 현황 조회 및 수정, 삭제를 할 수 있습니다.
- 전자 결재에서 승인된 연차 결재를 사원별로 개별 조회 가능합니다.