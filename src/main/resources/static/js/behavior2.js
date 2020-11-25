$(function () {
    echart_1();
    echart_2();
    echart_3();

    function echart_1() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('chart_1'));
        var xAxisData = [];
        var legendData = ['x', 'y', 'z'];
        var serieData = [];
        var title = "";  //很神奇的是这个标题绝对不能删除！！！！！！！！！！！！！！！！why？？？？？？
        var metaDate = [
            [0.263183594,0.739306641,0.844580078,1.4546875,4.940673828,13.81713867,13.22617188 ,-9.563134766,-44.36796875 ,-12.10166016,4.416699219,2.433251953 ,-0.703417969 ,-1.296777344 ,-0.11484375 ,-0.971386719 ,-1.335058594 ,-2.552880859, -3.756347656 ,-4.897607422 ,-4.289892578 ,-3.727636719 ,-3.091210938 ,-1.976269531 ,-0.758447266,1.062304688,3.084033203,7.986425781,21.4159668,10.81445313 ,-17.03754883 ,-29.50288086 ,-15.23354492,5.706298828 ,-0.299072266,0.878076172,1.739404297,1.18671875 ,-0.385205078 ,-2.019335938],
            [8.115625, 9.060693359, 9.759326172, 9.969873047, 10.21870117, 16.16425781, 14.11381836, 18.71474609, 9.661230469, 6.900195313, 11.06088867, 11.95092773, 7.644287109, 10.80249023, 9.335839844, 8.981738281, 8.357275391, 9.261669922, 9.4171875, 8.728125, 9.543994141, 9.967480469, 11.97724609, 10.63740234, 12.26914063, 11.44848633, 12.82421875, 13.72382813, 18.13813477, 21.85620117, -9.161181641, 32.11079102, 5.545996094, 9.524853516, 9.161181641, 8.223291016, 10.27851563, 9.931591797, 9.938769531, 9.634912109],
            [5.000488281, 5.170361328, 4.043457031, 2.981152344, 2.782568359, 0.406738281, -3.203662109, -2.56484375, 2.863916016, -4.237255859, 5.813964844, 2.33515625, 4.088916016, 2.636621094, 2.744287109, 2.502636719, 4.352099609, 4.153515625, 4.189404297, 2.241845703, 0.052636719, 0.971386719, 1.473828125, 2.033691406, 2.428466797, 3.442919922, 4.653564453, 5.603417969, -0.619677734, -0.294287109, -0.035888672, 5.421582031, -0.72734375, 6.225488281, 3.631933594, 3.605615234, 3.033789063, 4.220507813, 2.909375, 2.909375]
        ]

        for (var v = 0; v < legendData.length; v++) {
            var serie = {
                name: legendData[v],
                type: 'line',
                symbol: "circle",
                symbolSize: 5,
                data: metaDate[v]
            };
            serieData.push(serie);
        }
/*三条线的颜色，但第四个颜色是干嘛的还不知道*/
        var colors = ["#f1b92e", "#FFF", "#a0134f", "#5fc9c6"];
        var option = {
            // backgroundColor: '#0f375f',
            title: {
                text: title,
                textAlign: 'left',
                textStyle: {
                    color: "#fcfafa",
                    fontSize: "12",
                    fontWeight: "bold"
                }
            },
/*三条线的图例*/
            legend: {
                show: true,
                left: "center",
                data: legendData,
                y: "5%",
                itemWidth: 18,
                itemHeight: 12,
                textStyle: {
                    color: "#fcfbfb",
                    fontSize: 14
                },
            },
// /*右侧工具栏*/
//             toolbox: {
//                 orient: 'vertical',
//                 right: '1%',
//                 top: '20%',
//                 iconStyle: {
//                     color: '#fdfcfc',
//                     borderColor: '#fff',
//                     borderWidth: 1,
//                 },
//                 feature: {
//                     saveAsImage: {},
//                     magicType: {
//                         // show: true,
//                         type: ['line','bar','stack','tiled']
//                     }
//                 }
//             },

            color: colors,
            /*画图区域的栅格与边框的距离*/
            grid: {
                left: '1%',
                top: "22%",
                bottom: "0%",
                right: "8%",
                containLabel: true
            },
            /*不知道干嘛的*/
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                },
            },
            /*横坐标*/
            xAxis: [{
                type: 'category',
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#6173A3'
                    }
                },
                axisLabel: {
                    interval: 0,
                    textStyle: {
                        color: '#9ea7c4',
                        fontSize: 12
                    }
                },
                axisTick: {
                    show: false
                },
                data: xAxisData,
            }, ],
            /*纵坐标*/
            yAxis: [{
                axisTick: {  // 纵坐标轴指向刻度的小标线
                    show: false
                },
                splitLine: {  // 纵坐标的栅格
                    show: true
                },
                axisLabel: {  // 坐标轴的刻度
                    textStyle: {
                        color: '#9ea7c4',
                        fontSize: 12
                    }
                },
                axisLine: {  // 坐标轴的线
                    show: true,
                    lineStyle: {
                        color: '#6173A3'
                    }
                },
            }, ],

            series: serieData
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }



    function echart_2() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('chart_2'));  
        var giftImageUrl = "";  // 环形图中间的图标
        myChart.setOption({
            /*环形图中间小图标的属性*/
            graphic: {
                elements: [{
                    type: 'image',
                    style: {
                        image: giftImageUrl,
                        width: 30,
                        height: 30
                    },
                    left: '73%',
                    top: 'center'
                }]
            },
            /**/
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            /*画图区域的栅格与边框的距离*/
            grid: {
                left: '1%',
                right: '60%',
                top: '10%',
                bottom: '10%',
                containLabel: true,
            },
            /*x轴*/
            xAxis: {
                type: 'value',
                position:'top',
                splitLine: {show: false},
                boundaryGap: [0, 0.1],
                axisTick: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#9ea7c4',
                        fontSize: 12
                    }
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#6173A3'
                    }
                },
            },
            /*y轴*/
            yAxis: {
                type: 'category',
                data: ['A','B','E','H','punch_down','punch_straight'],
                axisTick: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#9ea7c4',
                        fontSize: 12
                    }
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#6173A3'
                    }
                },
            },
            /**/
            series: [
                /*条形图*/
                {
                name: '',
                itemStyle: {
                    normal: {
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                                '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                            ];
                            return colorList[params.dataIndex]
                        },
                        shadowBlur: 20,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'  // 条形图阴影
                    }
                },
                type: 'bar',
                    data: [4220,4200,2400,4799,24805,27350]
            },
                /*环形图*/
                {
                type: 'pie',
                radius: [30, '65%'],  // 环形图外围环的宽度（中间的大小）；整个环状图的大小
                center: ['75%', '50%'],  // 相对位置
                roseType: 'radius',
                color: [ '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                '#D7504B','#C6E579','#F4E001','#f0805a','#26C0C0'],
                data: [{
                    value: 6.23,
                    name: 'A'
                }, {
                    value: 6.20,
                    name: 'B'
                }, {
                    value: 3.54,
                    name: 'E'
                }, {
                    value: 7.08,
                    name: 'H'
                }, {
                    value: 36.60,
                    name: 'punch_down'
                },{
                    value: 40.35,
                    name: 'punch_straight'
                }],
                /*环形图标签文字*/
                label: {
                    normal: {
                        textStyle: {
                            fontSize: 14
                        },
                        formatter: function(param) {
                            return param.name + ': ' + Math.round(param.percent) + '%';
                        }
                    }
                },

                labelLine: {  // 环形图标签引线
                    normal: {
                        smooth: true,
                        lineStyle: {
                            width: 2
                        }
                    }
                },

                itemStyle: {
                    normal: {
                        shadowBlur: 30,
                        shadowColor: 'rgba(0, 0, 0, 0.4)'  // 环形图阴影颜色
                    }
                },
       
                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function(idx) {
                    return Math.random() * 200;
                }
            }]
        });
    }
});