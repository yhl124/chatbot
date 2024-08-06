Highcharts.chart('bargraph', {
    chart: {
        type: 'column', // 막대그래프 형식으로 변경
        marginTop: 70 // 차트의 상단 여백을 조절하여 제목 아래 공간을 추가
    },
    credits: {
        enabled: false // Highcharts 로고 비활성화
    },
    title: {
        text: '월별 정책 개수',
        align: 'left'
    },
    subtitle: {
        text: '', // 부제목은 공백으로 설정
        align: 'left',
        verticalAlign: 'bottom',
        y: 40 // 부제목의 위치 조정
    },
    xAxis: {
        categories: ['교육', '일자리', '주거', '복지 문화', '참여 권리'],
        crosshair: true,
        accessibility: {
            description: 'Policy Categories'
        }
    },
    yAxis: {
        min: 0,
        max: 50, // y축 최대 값 50으로 설정
        title: {
            text: '정책수'
        },
        allowDecimals: false // 소수점 없이 표시
    },
    tooltip: {
        valueSuffix: ' policies'
    },
    plotOptions: {
        column: {
            pointPadding: 0.1, // 막대 내부의 패딩을 줄임
            groupPadding: 0.1, // 막대 사이의 간격을 줄임
            borderWidth: 0,
            dataLabels: {
                enabled: true,
                format: '{y}개' // 데이터 라벨 포맷 설정
            }
        }
    },
    series: [{
        name: 'Categories',
        data: [
            { y: 30, color: '#90CAF9' }, //'#7cb5ec'
            { y: 40, color: '#9575CD' }, //'#f08080'
            { y: 25, color: '#BBDEFB' }, //'#cda2d5'
            { y: 35, color: '#D1C4E9' }, //'#f7a35c'
            { y: 30, color: '#673AB7' } //'#8085e9'
        ]
    }],
    legend: {
        enabled: false // 범례 비활성화
    },
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    enabled: false // 작은 화면에서도 범례 비활성화
                }
            }
        }]
    }
});