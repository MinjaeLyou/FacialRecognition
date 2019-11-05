<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="5"> 
          <h4 id="traffic" class="card-title mb-0">KT 개발자 포탈</h4>
          <div class="small text-muted">KT DEV Community Guide</div>
        </b-col>
        <b-col sm="7" class="d-none d-md-block">
        </b-col>
        <div class="div-padding" v-html="guide.text">&nbsp;
        </div>
      </b-row>
    </b-card>
    
    <b-row>
            

    
      <div class="col-sm-6 col-md-6 col-lg-4">
        <b-card header="상반기 IoT 제휴">
        <div class="chart-wrapper">
          <line-example chartId="chart-line-01"/>
        </div>
      </b-card>
      </div> 

    <div class="col-sm-6 col-md-6 col-lg-4">
      <b-card header="사용자 발화 패턴">
        <div class="chart-wrapper">
          <doughnut-example chartId="chart-doughnut-01"/>
        </div>
      </b-card>
      </div>
    <div class="col-sm-6 col-md-6 col-lg-4">
      <b-card header="사용자 기기 제어">
        <div class="chart-wrapper">
          <bar-example chartId="chart-bar-01"/>
        </div>
      </b-card>   
    </div>  
    </b-row>
    
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
    <b-row>
    <div class="col-sm-6 col-md-6 col-lg-4">
        <div class="card">
          <div class="card-body">
            <div style="display:inline" class="h4 m-0" > 30 </div><small class="text-muted">registered users </small> 
            <div> {{ weekquiz.name }} 가입자 수 </div>
            <div class="progress-xs my-3 progress">
              <div role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="25" class="progress-bar bg-red" style="width: 100%">  
              </div>
            </div>
            <small class="text-muted">남은 시간 {{dRound}} day {{hRound}} hr {{mRound}} min </small>
          </div>
        </div>
      </div>

      <div class="col-sm-6 col-md-6 col-lg-4">
        <div class="card">
          <div class="card-body">
            <div style="display:inline" class="h4 m-0"> 22</div> <small class="text-muted">&nbsp; 3<sup>rd</sup> party companies</small> 
            <div> 제휴사 수 </div>
            <div class="progress-xs my-3 progress">
              <div role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="25" class="progress-bar bg-success" style="width: 100%;">  </div>
            </div>
            <small class="text-muted">&nbsp;</small>
          </div>
        </div>
      </div>

      <div class="col-sm-6 col-md-6 col-lg-4">
        <div class="card">  
          <div class="card-body">
            <div style="display:inline" class="h4 m-0"> 5</div> <small class="text-muted">&nbsp; applied companies</small> 
            <div> 제휴 신청 </div>
            <!-- submitted length 받아오는 것 수정해야 됨 -->
            <div class="progress-xs my-3 progress">
              <div role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="25" class="progress-bar bg-warning" style="width: 100%;">  </div>
            </div>
            <small class="text-muted">&nbsp;</small>
          </div>
        </div>
      </div>
      </b-row>
  </div>
</template>

<script>
import CardBarChartExample from './dashboard/CardBarChartExample'
import MainChartExample from './dashboard/MainChartExample'
import SocialBoxChartExample from './dashboard/SocialBoxChartExample'
import CalloutChartExample from './dashboard/CalloutChartExample'
import { Callout } from '@coreui/vue'
import LineExample from './charts/LineExample'
import DoughnutExample from './charts/DoughnutExample'
import BarExample from './charts/BarExample'

export default {
  name: 'dashboard',
  components: {
    Callout,
    CardBarChartExample,
    MainChartExample,
    SocialBoxChartExample,
    CalloutChartExample,
    LineExample,
    DoughnutExample,
    BarExample,
  },
  data() {
    return {
      users: '',
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
    }
  },
  mounted: function() {
    this.userNumber();
    this.getNotice();
    this.getGuide();
  },
  created: async function() {
      const result = await this.$http.get(`/weeklyquiz/problem`)
      this.weekquiz = result.data;
      const res = await this.$http.get(`/weeklyquiz/submission/${this.weekquiz.problem_id}`)
      this.submissionUser = res.data
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
      this.guide.text = "<p style='margin-top: 10px;'>\n&nbsp;&nbsp;&nbsp;&nbsp;제휴 신청과 모델 등록을 한 포탈에서 해보세요.</p>"
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
</style>
