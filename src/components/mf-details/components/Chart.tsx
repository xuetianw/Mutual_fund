import React from "react";
import '../stylesheets/Chart.css'
import { Line } from "react-chartjs-2";
function LineChart({ chartData }: any) {
    const dataConfig = {
        labels: chartData.mfDetailHistory.map((data:any) => new Date(data.date).toLocaleDateString("en-US",{month:'short', day:'2-digit'})),
        datasets: [
            {
                label: "Price",
                data: chartData.mfDetailHistory.map((data: any) => data.nav),
                backgroundColor: [
                    "rgba(75,192,192,1)",
                  
                ],
                hitRadius: 30,
                hoverRadius:10,
                pointStyle:'circle',
                borderColor: "grey",
                borderWidth: 1,
                fill: {
                    target: true,
                    above: 'rgb(78,78,148,0.7)'
                }
            }]
    };

    return (

        <div className="chart-container">
            <Line
                data={dataConfig}
                options={{
                    animations: {
                        tension: {
                            duration: 1000,
                            easing: 'linear',
                            from: 0.5,
                            to: 0,
                            loop: false
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            //text: `${chartData.symbol} - ${chartData.schemaName}`,
                            color: "black"
                        },
                        legend: {
                            display: false
                        },

                    },
                    responsive: true,
                    maintainAspectRatio: true
                }}

            />
        </div>
    );
}
export default LineChart;

// let CanvasJS = CanvasJSReact.CanvasJS;
// let CanvasJSChart = CanvasJSReact.CanvasJSChart;

// const options = {
//     animationEnabled: true,
//     theme: "light2",
//     title:{
//         text: "Mutual Fund Name here"
//     },
//     axisX:{
//         valueFormatString: "DD MMM",
//         crosshair: {
//             enabled: true,
//             snapToDataPoint: true
//         }
//     },
//     axisY: {
//         title: "Closing Price (in USD)",
//         valueFormatString: "$##0.00",
//         crosshair: {
//             enabled: true,
//             snapToDataPoint: true,
//             labelFormatter: function(e:any) {
//                 return "$" + CanvasJS.formatNumber(e.value, "##0.00");
//             }
//         }
//     },
//     data: [{
//         type: "area",
//         xValueFormatString: "DD MMM",
//         yValueFormatString: "$##0.00",
//         dataPoints: [
//           { x: new Date("2018-03-01"), y: 85.3},
//           { x: new Date("2018-03-02"), y: 83.97},
//           { x: new Date("2018-03-05"), y: 83.49},
//           { x: new Date("2018-03-06"), y: 84.16},
//           { x: new Date("2018-03-07"), y: 84.86},
//           { x: new Date("2018-03-08"), y: 84.97},
//           { x: new Date("2018-03-09"), y: 85.13},
//           { x: new Date("2018-03-12"), y: 85.71},
//           { x: new Date("2018-03-13"), y: 84.63},
//           { x: new Date("2018-03-14"), y: 84.17},
//           { x: new Date("2018-03-15"), y: 85.12},
//           { x: new Date("2018-03-16"), y: 85.86},
//           { x: new Date("2018-03-19"), y: 85.17},
//           { x: new Date("2018-03-20"), y: 85.99},
//           { x: new Date("2018-03-21"), y: 86.1},
//           { x: new Date("2018-03-22"), y: 85.33},
//           { x: new Date("2018-03-23"), y: 84.18},
//           { x: new Date("2018-03-26"), y: 85.21},
//           { x: new Date("2018-03-27"), y: 85.81},
//           { x: new Date("2018-03-28"), y: 85.56},
//           { x: new Date("2018-03-29"), y: 88.15}
//         ]
//     }]
// }

// return (
//     <div>
//         <CanvasJSChart options = {options}
//             /* onRef={ref => this.chart = ref} */
//         />
//         {/*You can get reference to the chart instance as shown above using onRef. This allows you to access all chart properties and methods*/}
//     </div>
//     );