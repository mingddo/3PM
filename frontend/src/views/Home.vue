<template>
  <div class="home">
    <div v-if="!userStatus">
      <button class="login-btn" @click="onClickLogin">카카오로그인</button>
    </div>
    <div v-else>
        <div @click="goToPersonal">
          <i class="fas fa-house-user">꽃보다 집</i>
        </div>
      <button  class="login-btn" style="z-index:1000" @click="onClickLogout">로그아웃</button>
    </div>
    <!-- <button class="login-btn" @click="deleteKakaoConnection">회원연결끊기</button> -->
    <section class="home-section">
      <h1>오후 세시.</h1>
    </section>
    <section class="home-section">
      <h1>오후 네시.</h1>
    </section>
    <section class="home-section">
      <h1>오후 다섯시.</h1>
    </section>
  </div>
</template>

<script>
import {mapActions,mapState} from 'vuex'
import { getUser } from '@/api/login.js'

export default {
  name: 'Home',
  data () {
    return {
    }
  },
  computed : {
    ...mapState(['userStatus','kakaoId','userId','authToken']),
  },
  methods : {
    ...mapActions(['setUserStatus','setAuthToken','setKakaoId','setUserId']),
    scrollStatus() {
      let viewportHeight = window.innerHeight;
      let scrollPos = window.scrollY
      if (scrollPos > viewportHeight * 0.05) {
        this.loginBtn.classList.add("login-btn-moved")
      }
      else {
        this.loginBtn.classList.remove("login-btn-moved");
      }
    },
    kakaoLogin() {
      window.Kakao.Auth.login({
        scope : 'account_email, gender, age_range',
        success : (authObj) => {
          console.log(authObj);
          this.kakaoAccoutInfo();   
        },
        fail : (err) => {
          alert(err)
        }
      });
    },

    kakaoAccoutInfo() {
      window.Kakao.API.request({
        url:'/v2/user/me',
      })
      .then((res)=>{
        const kakaoId = res.id; 
        console.log(res)
        getUser(
          {
            "kakaoId" : kakaoId,
          },
          (res) => {
            this.setKakaoId(kakaoId)
            // true -> user 정보가 있으면  Home
            if(res.data) {
              // 세션에 토큰 설정
              console.log(res.data)
              const responseUserId = res.data.id
              const authToken = res.data['auth_token']
              this.setUserId(responseUserId)
              this.setAuthToken(authToken)
              this.setUserStatus(true)
            }
            // false -> user 정보가 없으면 Signup
            else {
              // 세션에 카카오 ID 설정
              this.$router.push({name : "Signup"});
            }
          },
          (err) => {
            console.log(err)
          }
        )
      })
      .catch((err)=>{
        console.log('err',err);
      })
    },

    kakaoGetAccessToken() {
      return window.Kakao.Auth.getAccessToken();
    },

    kakaoLogout() {
      window.Kakao.Auth.logout(()=>{ 
        this.setUserId(null)
        this.setAuthToken(null)
        this.setKakaoId(null)
        this.setUserStatus(null)
        alert('logout')
      })
    },

    deleteKakaoConnection() { // 카카오, 오후 세시 연결 끊기 === 회원 탈퇴
      window.Kakao.API.request({
        url : '/v1/user/unlink',
      })
      .then((res) => {
        localStorage.clear();
        console.log(res)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    onClickLogin() {
      this.kakaoLogin();
    },

    onClickLogout() {
      this.kakaoLogout();
    },
    goToPersonal () {
      this.$router.push({name: 'NewsfeedPersonal', query: { Category: '꽃보다 집'}})
    },
    // onClickDeleteUserConnection() {
    //   deleteKakaoConnection();
    // }
  },
  mounted () {
    if(!this.isLoginUser) {
      this.loginBtn = document.querySelector('.login-btn');
      this.loginBtnText = document.querySelector('.login-btn-text');
      window.addEventListener("scroll",this.scrollStatus);
    }
  },
}
</script>

<style scoped>
  
</style>