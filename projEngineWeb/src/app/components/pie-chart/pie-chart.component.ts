import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  constructor() {
    
  }

  ngOnInit(): void {
      
  }

  initOpts = {
    renderer: "svg",
    width: 600,
    height: 600
  };

  options : any = {
    title: {
      text: "Medicare Plan Type",
      left: "center"
    },
    tooltip: {
      trigger: "item",
      width: "100%"
    },
    legend: {
      top: "90%",
      left: "center"
    },
    series: [
      {
        name: "Medicare Plan Type",
        type: "pie",
        radius: "80%",
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: "#fff",
          borderWidth: 2
        },
        label: {
          show: false,
          position: "center"
        },
        emphasis: {
          label: {
            show: false,
            fontSize: "40",
            fontWeight: "bold"
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: "STCOB Secondary" },
          { value: 735, name: "Part B Medial Only (MA)" },
          { value: 580, name: "Defined Standard (B01)" },
          { value: 484, name: "N/A (MA or STCOB Only)" },
          {
            value: 300,
            name: "Alternative Basic or Actuarilly Equivalent Standard (ALB)"
          }
        ]
      }
    ]
  };

}
