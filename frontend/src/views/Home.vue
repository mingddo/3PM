<template>
  <div class="home">
    <button v-if="!userStatus" class="login-btn" @click="onClickLogin">카카오로그인</button>
    <button v-else class="login-btn" style="z-index:1000" @click="onClickLogout">로그아웃</button>
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
    ...mapState(['userStatus','kakaoId']),
  },
  methods : {
    ...mapActions(['setUserStatus','setAuthToken','setKakaoId']),
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
        const userId = res.id;
        console.log(res)
        getUser(
          {
            "kakaoId" : userId,
          },
          (res) => {
            console.log(res)
            // true -> user 정보가 있으면  Home
            this.setKakaoId(userId)
            if(res.data) {
              // 세션에 토큰 설정
              const authToken = res.data['auth-token']
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
        this.setUserStatus(false)
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
      // this.setUserStatus(true)

    },

    onClickLogout() {
      this.kakaoLogout();
      // this.setUserStatus(false)
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
  .home-section {
    height: 100vh;
    padding-top: 5vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
  }

  .login-btn {
    width: 120px;
    position: absolute;
    top: 5vh;
    right: 5%;
    font-weight: bold;
    color: #2c3e50;
    transition: all 0.8s ease-in-out;
    padding : 0.25rem 1rem;
    background: none;
    border: none;
    box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
    border-radius: 30px;
    cursor: pointer;
    font-weight: bold;
    color: #585858;
  }
  .login-btn.active {
    background: #585858;
    color: white;
  }
  .login-btn-moved {
    width: 100%;
    position: fixed;
    font-weight: bold;
    color: #2c3e50;
    transition: all 1s ease-in-out;
    top: 90vh;   
    right: 0; 
    border-radius: 0px;

  }
</style>