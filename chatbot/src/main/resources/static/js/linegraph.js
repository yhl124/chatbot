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
        categories: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
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
    series: [{
        name: '교육',
        data: [10, 12, 14, 18, 21, 25, 27, 26, 24, 22, 19, 15],
        color: '#90CAF9' //'#7cb5ec'
    }, {
        name: '일자리',
        data: [5, 7, 6, 9, 15, 22, 21, 20, 19, 14, 9, 6],
        color: '#9575CD' //'#f08080'
    }, {
        name: '주거',
        data: [20, 22, 25, 28, 33, 38, 37, 35, 32, 28, 25, 22],
        color: '#BBDEFB' //'#cda2d5'
    }, {
        name: '복지 문화',
        data: [8, 10, 12, 15, 17, 20, 18, 17, 15, 13, 11, 9],
        color: '#D1C4E9' //'#f7a35c'
    }, {
        name: '참여 권리',
        data: [0, 0, 0, 20, 23, 25, 27, 25, 23, 20, 18, 16],
        color: '#673AB7' //'#8085e9'
    }],
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