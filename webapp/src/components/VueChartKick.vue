<script>
  //Importing Line class from the vue-chartjs wrapper
  import {Line} from 'vue-chartjs'
  //Exporting this so it can be used in other components
  export default { 
    extends: Line,
    data () {
      return {
        lineloaded: false,
        lineData: null,
        //Chart.js options that controls the appearance of the chart
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              }
            }],
            xAxes: [ {
              gridLines: {
                display: false
              }
            }]
          },
          legend: {
            display: true
          },
          responsive: true,
          maintainAspectRatio: false
        }
      }
    },
    mounted: async function () {
      //renderChart function renders the chart with the datacollection and options object.
      this.renderChart(this.datacollection, this.options)
      const res = await this.$http.get(`http://localhost:3000/face/getAllResult/lmj`)
      console.log(res)
      let ldata = []
      let date = []
      for(let i = 0; i < 20; i++){
        date[i] = await res.data[i].createdAt.split(' ')[0]
          ldata[i] = await res.data[i].result
      }
      this.lineData = {
        labels: date, 
          datasets: [
            {
              label: '최근 결과값 추이',
              backgroundColor: '#A3BAF6',
              pointBackgroundColor: 'white',
              borderWidth: 1,
              pointBorderColor: '#249EBF',
              //Data to be represented on y-axis
              data: ldata
            }
          ]
      }
      console.log(this.datacollection.datasets[0].data)
      
    }
  }
</script>
