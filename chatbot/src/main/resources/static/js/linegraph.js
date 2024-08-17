// Highcharts 차트 생성
Highcharts.chart('linegraph', {
    chart: {
        type: 'spline', // 선그래프 형식으로 변경
        marginTop: 70 // 차트의 상단 여백을 조절하여 제목 아래 공간을 추가
    },
    credits: {
        enabled: false // Highcharts 로고 비활성화
    },
    title: {
        text: '카테고리별 월별 정책 수',
        align: 'left'
    },
    subtitle: {
        text: '', // 부제목은 공백으로 설정
        align: 'left',
        verticalAlign: 'bottom',
        y: 40 // 부제목의 위치 조정
    },
    yAxis: {
        title: {
            text: '정책수'
        },
        labels: {
            format: '{value}'
        }
    },
    xAxis: {
        categories: categories, // 동적으로 생성된 월별 카테고리 적용
        accessibility: {
            description: 'Months of the year'
        }
    },
    tooltip: {
        crosshairs: true,
        shared: true
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },
    plotOptions: {
        spline: {
            marker: {
                radius: 4,
                lineColor: '#666666',
                lineWidth: 1
            }
        }
    },
    series: series, // 동적으로 생성된 series 데이터 적용
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom'
                }
            }
        }]
    }
});