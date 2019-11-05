<script>
  //Importing Line class from the vue-chartjs wrapper
  import {Line} from 'vue-chartjs'
  //Exporting this so it can be used in other components
  export default { 
    extends: Line,
    data () {
      return {
        datacollection: {
          //Data to be represented on x-axis
          labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 
                    'August', 'September', 'October', 'November', 'December'], 
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#A3BAF6',
              pointBackgroundColor: 'white',
              borderWidth: 1,
              pointBorderColor: '#249EBF',
              //Data to be represented on y-axis
              data: [40, 20, 30, 50, 90, 10, 20, 40, 50, 70, 90, 100]
            }
          ]
        },
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
      for(let i = 0; i < 20; i++){
          this.datacollection.datasets[0].data[i] = await res.data[i].result
      }
      console.log(this.datacollection.datasets[0].data)
      
    }
  }
</script>
