<template>
  <div id="mainapp">
    <b-card style="margin-bottom:10px;">
      <b-row>
        <b-col sm="5"> 
          <h4 id="traffic" class="card-title mb-0">{{user.name}}님 환영합니다!</h4>
          <div class="small text-muted">Facial Recognition for Modern People</div>
        </b-col>
        <b-col sm="7" class="d-none d-md-block">
        </b-col>
        <div class="div-padding" v-html="guide.text">&nbsp;
        </div>
      </b-row>
    </b-card>
    <b-card style="margin-bottom:10px;">
      <b-row>
              

      
        <div class="columns" style="margin: 0 30px;">
          <div class="column">
              <h3>최근 결과값 추이</h3>
              <line-chart v-if="lineloaded" :chartdata="linedata" :options="lineop"></line-chart>
          </div>
          
        </div>

        <div class="columns" style="margin: 0 30px;">
          <div class="column">
              <h3>최근 표정 분포</h3>
              <pie-chart v-if="pieloaded" :data="pieData" :options="pieOptions"></pie-chart>
          </div>
          
        </div>
        <div class="columns" style="margin: 0 30px;">
          <div class="column">
              <h3>Line Chart</h3>
              <vue-chart v-if="loaded" :chartdata="cdata" :options="cop"></vue-chart>
          </div>
          
        </div>  
      </b-row>
    </b-card>
    
    <div class="card">
      <div class="card-header">
        새소식
        <small>Notice</small>
      </div>
      <div class="card-body">
        <table class="table table-margin">
          <tbody id="news">
            <tr class="tr-cursor" v-for="n in recentnotice" v-on:click="viewRecentNotice(n.notice_id)">
              <td> {{n.title}} </td>
              <td> {{n.createdAt.slice(0,10)}} </td>
              <td> {{n.author}} </td>
            </tr>
            <tr class="tr-size">
              <td class="td-padding"></td>
              <td class="td-padding"></td>
              <td class="td-padding"></td>
            </tr>
          </tbody>
        </table>
        
      </div>
    </div>
  </div>
</template>

<script>
import LineChart from '@/components/LineChart'
import { Callout } from '@coreui/vue'
import PieChart from "./PieChart.js";
import VueChart from '@/components/VueChartJS'
export default {
  name: 'dashboard',
  components: {
    Callout,
    LineChart,
    PieChart,
    VueChart
  },
  data() {
    return {
      user: '',
      weekquiz: {},
      guide: {},
      recentnotice: [],
      now: 0,
      dday:0,
      days:0,
      dRound:0, 
      hours:0,
      hRound:0,
      minutes:0,
      mRound:0,
      seconds:0, 
      sRound:0,
      submitted:0,
      totalSubmission:0,
      pieOptions: {
        hoverBorderWidth: 20
      },
      pieData: null,
      cdata: null,
      cop: null,
      loaded: false,
      pieloaded: false,
      lineloaded: false,
      linedata: null,
      lineop: null
    }
  },
  mounted: async function() {
    this.loaded = false
    this.lineloaded = false
    this.pieloaded = false
    const res = await this.$http.get(`http://localhost:3000/face/getAllResult/lmj`)
    let pos = 0
    let neg = 0
    let ldata = []
    let date = []
    let pie = [0,0,0,0,0]
    for(let i = 0; i < 20; i++){
      date[i] = await res.data[i].createdAt.split(' ')[0]
      ldata[i] = await parseFloat(res.data[i].result)
      if(res.data[i].posOrNeg)
        pos += 1
      else
        neg += 1
      
      if(res.data[i].result < 0.2)
        pie[0] += 1
      else if(res.data[i].result < 0.4)
        pie[1] += 1
      else if(res.data[i].result < 0.6)
        pie[2] += 1
      else if(res.data[i].result < 0.8)
        pie[3] += 1
      else
        pie[4] += 1
    }
    console.log(date)
    console.log(ldata)
    this.loaded = true
    this.lineloaded = true
    this.pieloaded = true
    this.cdata = {
      labels: ["긍정", "부정"],
        datasets: [
          {
            label: "긍정 / 부정",
            backgroundColor: ["rgb(255, 99, 132)", "rgb(54, 162, 235)"],
            data: [pos, neg]
          }
        ]
    }
    this.cop = {
      scales:{
        yAxes: [
          {
            ticks:{
              beginAtZero: true
            }
          }
        ]
      }
    }
    this.linedata = {
      labels: date, 
          datasets: [
            {
              label: '결과값',
              backgroundColor: '#A3BAF6',
              pointBackgroundColor: 'white',
              borderWidth: 1,
              pointBorderColor: '#249EBF',
              //Data to be represented on y-axis
              data: ldata
            }
          ]
    }
    this.lineop = {
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
    this.pieData = {
        hoverBackgroundColor: "red",
        hoverBorderWidth: 10,
        labels: ["매우 긍정", "긍정", "보통", "부정", "매우 부정"],
        datasets: [
          {
            label: "Data One",
            backgroundColor: ["#41B883", "#E46651", "#00D8FF", "#A3BAF6", "#B654FB"],
            data: pie
          }
        ]
      }
    //this.userNumber();
    this.getNotice();
    this.getGuide();
    
  },
  created: async function() {
    if(!this.$session.exists()){
      alert("로그인이 필요한 서비스입니다.")
      this.$router.push({name: "Login"})
    }
      const res = await this.$http.get(`http://localhost:3000/users/getUser/lmj`)
      this.user = res.data
      console.log(this.$session.getAll())
  },
  methods: {
    async getNotice() {
      this.recentnotice = [{'title': " App New Version 2.0", createdAt: '2019-11-03', author: 'MJ Lyou'}, {title: ' You can download this app now!', createdAt: '2019-10-20', author: 'MJ Lyou'}]
      console.log(this.recentnotice)
    },
    async getGuide() {
      // this.$http.get(`/weeklyquiz/notice/0`).then((result) => {
      //   this.guide = result.data;
      // });
      this.guide.text = "<p style='margin-top: 10px;'>\n&nbsp;&nbsp;&nbsp;&nbsp;표정을 객관적인 지표로 확인해보세요.</p>"
    },
    async viewRecentNotice(id) {
      this.$router.push({'path' : '/notice/' +id})
    },
  }
}
</script>

<style>
  /* IE fix */
  #card-chart-01, #card-chart-02 {
    width: 100% !important;
  }

  #mainapp {
    margin: 10px;
  }

  b-card {
    margin: 5px;
  }
</style>
