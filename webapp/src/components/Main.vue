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
              <h3>Line Chart</h3>
              <line-chart></line-chart>
          </div>
          
        </div>

        <div class="columns" style="margin: 0 30px;">
          <div class="column">
              <h3>최근 표정 분포(20회)</h3>
              <pie-chart :data="chartData" :options="chartOptions"></pie-chart>
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
        신규 소식
        <small>News</small>
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
      chartOptions: {
        hoverBorderWidth: 20
      },
      chartData: {
        hoverBackgroundColor: "red",
        hoverBorderWidth: 10,
        labels: ["매우 긍정", "긍정", "보통", "부정", "매우 부정"],
        datasets: [
          {
            label: "Data One",
            backgroundColor: ["#41B883", "#E46651", "#00D8FF", "#A3BAF6", "#B654FB"],
            data: [40, 12, 16, 4, 28]
          }
        ]
      },
      cdata: null,
      cop: null,
      loaded: false
    }
  },
  mounted: async function() {
    this.loaded = false
    const res = await this.$http.get(`http://localhost:3000/face/getAllResult/lmj`)
    let pos = 0
    let neg = 0
    for(let i = 0; i < 20; i++){
      if(res.data[i].posOrNeg)
        pos += 1
      else
        neg += 1
    }
    console.log(neg)
    this.loaded = true
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
    //this.userNumber();
    this.getNotice();
    this.getGuide();
    
  },
  created: async function() {
    print("create")
      //const result = await this.$http.get(`/weeklyquiz/problem`)
      //this.weekquiz = result.data;
      const res = await this.$http.get(`http://localhost:3000/users/getUser/lmj`)
      this.user = res.data
      this.totalSubmission = this.submissionUser.length;
      this.getTime();
  },
  methods: {
    variant (value) {
      let $variant
      if (value <= 25) {
        $variant = 'info'
      } else if (value > 25 && value <= 50) {
        $variant = 'success'
      } else if (value > 50 && value <= 75) {
        $variant = 'warning'
      } else if (value > 75 && value <= 100) {
        $variant = 'danger'
      }
      return $variant
    },
    flag (value) {
      return 'flag-icon flag-icon-' + value
    },
    async userNumber() {
      const result = await this.$http.get(`/weeklyquiz/usercount`)
      this.users = result.data.count;
    },
    getTime() { 
      this.now = new Date();
      this.dday = new Date(this.weekquiz.dueDate);
      this.days = (this.dday - this.now) / 1000 / 60 / 60 / 24; 
      this.dRound = Math.floor(this.days); 
      this.hours = (this.dday - this.now) / 1000 / 60 / 60 - (24 * this.dRound); 
      this.hRound = Math.floor(this.hours); 
      this.minutes = (this.dday - this.now) / 1000 /60 - (24 * 60 * this.dRound) - (60 * this.hRound); 
      this.mRound = Math.floor(this.minutes); 
      this.seconds = (this.dday - this.now) / 1000 - (24 * 60 * 60 * this.dRound) - (60 * 60 * this.hRound) - (60 * this.mRound); 
      this.sRound = Math.round(this.seconds);
    },
    async getNotice() {
      const count = 5;
      //const result = await this.$http.get      (`/weeklyquiz/recentnotice/`+count)
      //this.recentnotice = result.data;
      this.recentnotice = [{'title': " 모델 등록 방법(KT 표준)", createdAt: '2019-08-18', author: 'KT AI IoT'}, {title: ' 제휴 신청 방법 및 양식', createdAt: '2019-08-20', author: 'KT AI IoT'}]
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
