// 1. main.js : 새로고침시, 초기 차트 설정 및 라디오 갱신
document.addEventListener('DOMContentLoaded', function () {
    // 차트 초기화 및 이벤트 리스너 설정
    const initialChart = ChartModule.initChart(); // 초기 차트 데이터 제공
    initialChart.render();

    // 서버에서 차트 데이터를 가져와서 적용
    $.ajax({
        url: '/chart/data',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log('서버로부터 받은 데이터:', data);

            // 페이지 로드 시 체크된 라디오 버튼의 값으로 차트를 업데이트
            const checkedRadio = document.querySelector('input:checked');
            if (checkedRadio) {
                const tab = checkedRadio.dataset.tab;
                const category = checkedRadio.value;
                ChartModule.updateChart(initialChart, tab, category, data);
            }

            // 차트 라디오 버튼 이벤트 리스너 설정
            EventListenerModule.attachChartRadioListeners(initialChart, data);
        },
        error: function(xhr, status, error) {
            console.error('차트 데이터를 가져오는데 실패했습니다:', error);
        }
    });
});

// 2. chartModule.js : 라디오 차트 생성 및 업데이트
const ChartModule = (function () {
    function initChart() {
        return new ApexCharts(document.querySelector("#barChart"), {
            series: [], // 빈 데이터로 초기화
            chart: {
                type: 'bar',
                height: 350,
                stacked: true, // 스택드 바 차트로 설정
                toolbar: {
                    show: false // 다운로드 옵션 비활성화
                }
            },
            plotOptions: {
                bar: {
                    borderRadius: 5,
                    horizontal: true
                }
            },
            dataLabels: {
                enabled: false
            },
            xaxis: {
                categories: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                max: 100
            },
            yaxis: {
                max: 100
            },
            fill: {
                opacity: 1
            },
            tooltip: {
                y: {
                    formatter: function (value, { series, seriesIndex, dataPointIndex, w }) {
                        const percentage = w.config.series[seriesIndex].name;
                        const totalValue = w.globals.seriesTotals[dataPointIndex];
                        const count = totalValue > 0 ? Math.round((value / 100) * totalValue) : 0;
                        return `${percentage} : ${count}건`;
                    }
                }
            }
        });
    }

    function updateChart(chart, tab, category, chartEntities) {
        if (chartEntities && chartEntities.length > 0) {
            let newData = [];
            let colors = [];

            if (tab === 'home' && category === '1') {
                // '부서' 월간 달성률 (누적 달성률) - 연두색 통일
                newData = calculateProgressData(chartEntities.filter(entity => entity.chartCategory === '부서'), false);
                colors = ['#93e6b7']; // 연두색 통일
            } else if (tab === 'home' && category === '2') {
                // '부서' 월별 진행률 (각 진행도에 따른 비율 계산)
                newData = calculateProgressDistribution(chartEntities.filter(entity => entity.chartCategory === '부서'));

                const series = [
                    { name: '0%', data: newData.map(item => item[0]) },
                    { name: '20%', data: newData.map(item => item[1]) },
                    { name: '40%', data: newData.map(item => item[2]) },
                    { name: '60%', data: newData.map(item => item[3]) },
                    { name: '80%', data: newData.map(item => item[4]) },
                    { name: '100%', data: newData.map(item => item[5]) }
                ];

                colors = ['#f16fc7', '#eed348', '#93e6b7', '#e4b8ff', '#58d68d', '#3498db'];

                chart.updateOptions({
                    series: series,
                    colors: colors
                });
                return;
            } else if (tab === 'profile' && category === '3') {
                // '개인' 월간 달성률 (누적 달성률) - 노란색 통일
                newData = calculateProgressData(chartEntities.filter(entity => entity.chartCategory === '개인'), false);
                colors = ['#eed348']; // 노란색 통일
            } else if (tab === 'profile' && category === '4') {
                // '개인' 월별 진행률 (각 진행도에 따른 비율 계산)
                newData = calculateProgressDistribution(chartEntities.filter(entity => entity.chartCategory === '개인'));

                const series = [
                    { name: '0%', data: newData.map(item => item[0]) },
                    { name: '20%', data: newData.map(item => item[1]) },
                    { name: '40%', data: newData.map(item => item[2]) },
                    { name: '60%', data: newData.map(item => item[3]) },
                    { name: '80%', data: newData.map(item[4]) },
                    { name: '100%', data: newData.map(item[5]) }
                ];

                colors = ['#f16fc7', '#eed348', '#93e6b7', '#e4b8ff', '#58d68d', '#3498db'];

                chart.updateOptions({
                    series: series,
                    colors: colors
                });
                return;
            } else {
                console.error('유효하지 않은 데이터: 탭 또는 카테고리를 찾을 수 없습니다');
                return;
            }

            chart.updateOptions({
                series: [{
                    data: newData
                }],
                colors: colors
            });
        } else {
            console.error('유효하지 않은 데이터: chartEntities가 정의되지 않았거나 비어 있습니다.');
        }
    }

    return {
        initChart,
        updateChart
    };
})();

// 3. eventListenerModule.js : 라디오 버튼에 맞는 탭 차트 업데이트
const EventListenerModule = (function (ChartModule) {
    function attachChartRadioListeners(chart, chartEntities) {
        document.querySelectorAll('.form-check-input').forEach(input => {
            input.addEventListener('change', event => {
                const tab = event.target.dataset.tab;
                const category = event.target.value;
                ChartModule.updateChart(chart, tab, category, chartEntities);
            });
        });
    }

    return {
        attachChartRadioListeners
    };
})(ChartModule);

// 4. chartDataCalculation.js : 라디오 차트 계산
function calculateProgressData(chartEntities, isCumulative) {
    const monthlyData = Array(12).fill(0);
    const totalCounts = Array(12).fill(0);
    const achievedCounts = Array(12).fill(0);

    chartEntities.forEach(entity => {
        const startDate = new Date(entity.chartStartDate);
        const month = startDate.getMonth();

        totalCounts[month] += 1;

        if (entity.noticePinned) {
            achievedCounts[month] += 1;
        }
    });

    if (isCumulative) {
        let cumulativeTotal = 0;
        let cumulativeAchieved = 0;

        for (let i = 0; i < 12; i++) {
            cumulativeTotal += totalCounts[i];
            cumulativeAchieved += achievedCounts[i];
            monthlyData[i] = cumulativeTotal > 0 ? (cumulativeAchieved / cumulativeTotal) * 100 : 0;
        }
    } else {
        for (let i = 0; i < 12; i++) {
            monthlyData[i] = totalCounts[i] > 0 ? (achievedCounts[i] / totalCounts[i]) * 100 : 0;
        }
    }

    return monthlyData;
}

function calculateProgressDistribution(chartEntities) {
    const monthlyData = Array.from({ length: 12 }, () => Array(6).fill(0));
    const totalCounts = Array(12).fill(0);

    chartEntities.forEach(entity => {
        const startDate = new Date(entity.chartStartDate);
        const month = startDate.getMonth();

        totalCounts[month] += 1;

        const progressIndex = Math.floor(entity.chartProgress / 20);
        if (progressIndex >= 0 && progressIndex < 6) {
            monthlyData[month][progressIndex] += 1;
        }
    });

    for (let i = 0; i < 12; i++) {
        if (totalCounts[i] > 0) {
            for (let j = 0; j < 6; j++) {
                monthlyData[i][j] = (monthlyData[i][j] / totalCounts[i]) * 100;
            }
        }
    }

    return monthlyData;
}
// 5. modalModule.js : 모달 차트 관리 (모달 및 날짜 초기화 )
const ModalModule = (function () {
    function initFlatpickr() {
        const chartPeriodInput = document.getElementById('chartPeriod');
        const chartStartDateInput = document.getElementById('chartStartDate');
        const chartEndDateInput = document.getElementById('chartEndDate');

        flatpickr(chartPeriodInput, {
            mode: "range",
            dateFormat: "Y-m-d",
            onChange: function (selectedDates, dateStr) {
                const dates = dateStr.split(" to ");
                chartStartDateInput.value = dates[0];
                chartEndDateInput.value = dates[1];
            }
        });
    }

    function showCreateChartModal() {
        const goalChartModal = new bootstrap.Modal(document.getElementById('goalChartModal'));
        goalChartModal.show();
    }

    function showCompareChartModal() {
        const compareChartModal = new bootstrap.Modal(document.getElementById('compareChartModal'));
        compareChartModal.show();
    }

    return {
        initFlatpickr,
        showCreateChartModal,
        showCompareChartModal
    };
})();

// 6. goalComparisonModule.js : 목표 비교 차트
const GoalComparisonModule = (function () {
    let selectedGoals = [];

    function initGoalComparison() {
        // 이벤트 리스너 설정
        document.querySelectorAll('.goal-checkbox').forEach(checkbox => {
            checkbox.addEventListener('change', function () {
                const goalId = this.value;
                const goalName = this.dataset.goalName;

                if (this.checked) {
                    if (selectedGoals.length < 3) {
                        selectedGoals.push({ id: goalId, name: goalName });
                    } else {
                        this.checked = false;
                        alert('최대 3개의 목표만 선택할 수 있습니다.');
                    }
                } else {
                    selectedGoals = selectedGoals.filter(g => g.id !== goalId);
                }

                updateSelectedGoalsList();
            });
        });

        document.getElementById('compareButton').addEventListener('click', function () {
            if (selectedGoals.length === 0) {
                alert('비교할 목표를 선택하세요.');
                return;
            }

            const chartNames = selectedGoals.map(goal => goal.name);
            const progressData = selectedGoals.map(goal => ({ name: goal.name, data: goal.progress }));

            const compareChart = new ApexCharts(document.querySelector("#compareChart"), {
                series: progressData,
                chart: {
                    type: 'bar',
                    height: 350
                },
                plotOptions: {
                    bar: {
                        horizontal: false,
                        columnWidth: '55%',
                        endingShape: 'rounded'
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    show: true,
                    width: 2,
                    colors: ['transparent']
                },
                xaxis: {
                    categories: chartNames,
                },
                yaxis: {
                    title: {
                        text: '% (퍼센트)'
                    }
                },
                fill: {
                    opacity: 1
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val + "%"
                        }
                    }
                }
            });

            compareChart.render();
        });

        document.getElementById('searchGoal').addEventListener('input', function () {
            const query = this.value.toLowerCase();
            document.querySelectorAll('.goal-checkbox').forEach(checkbox => {
                const goalName = checkbox.dataset.goalName.toLowerCase();
                const parent = checkbox.closest('.form-check');
                if (goalName.includes(query)) {
                    parent.style.display = '';
                } else {
                    parent.style.display = 'none';
                }
            });
        });
    }

    function updateSelectedGoalsList() {
        document.getElementById('selectedGoals').innerHTML = selectedGoals.map(g => `<li>${g.name}</li>`).join('');
    }

    return {
        initGoalComparison
    };
})();

