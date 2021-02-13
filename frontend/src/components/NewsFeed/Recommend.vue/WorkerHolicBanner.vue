<template>
  <div>
    <div class="banner-header">
      <h2>
        취업정보
      </h2>
      
    </div>
    <div class="banner-body">
      <div class="banner-body-each-banner" @click="goToPage1" v-if="age < 60">50세, 인생을 준비해볼까?</div>
      <div class="banner-body-each-banner" @click="goToPage2" v-else>60세, 새로운 일에 도전해볼 나이</div>
      <div class="banner-body-each-banner" @click="goToPage3">요즘 유튜브가 HOT 하다며~</div>
      <div class="banner-body-each-banner" @click="goToPage4">중장년 고용정책 한눈에 보기</div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'WorkerHolicBanner',
  data() {
    return {
      age : null,
    };
  },
  methods: {
    // texting

    //
    goToPage1 () {
      window.open("https://50plus.or.kr/")
    },
    goToPage2 () {
      window.open("https://www.seniorro.or.kr:4431/seniorro/main/main.do")
    },
    goToPage3 () {
      window.open("https://ko.wikihow.com/%EC%9C%A0%ED%8A%9C%EB%B8%8C-%EC%B1%84%EB%84%90-%EB%A7%8C%EB%93%9C%EB%8A%94-%EB%B2%95")
    },
    goToPage4 () {
      window.open("http://www.moel.go.kr/policy/policyinfo/aged/list.do")
    },
    ageCalc () {
      let start = this.userinfo.split("~")[0]
      this.age = start
    },
  },
  mounted () {
    this.ageCalc();
  },
  computed : {
    ...mapState({
      userinfo : (state) => state.userInfo,
    }),
  }
};
</script>

<style>
.banner-header {
  padding-top : 40px; 
  padding-bottom:40px; 
  text-align :center;
}
.banner-body {
  display:flex;
  justify-content:center;
  align-items:center;
  height:240px;
/* background-color:rgba(0,0,0,0.5); */
  flex-direction: column;
  align-content: space-between;
}
.banner-body:hover {
  box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
}
.banner-body-each-banner  {
  width:100%;
  cursor: pointer;
  justify-content: center;
  display: flex;
  height: 70px;
  margin: auto;
  background-color:rgba(0,0,0,0.5);
  align-items: center;
}
</style>