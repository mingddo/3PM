<template>
  <div class="home">
    <button v-if="!isLoginUser" class="login-btn" @click="kakaoLogin">카카오로그인</button>
    <button v-else class="login-btn" style="z-index:1000" @click="kakaoLogout">로그아웃</button>
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
import {mapState} from 'vuex'
import {mapActions} from 'vuex'

export default {
  name: 'Home',
  data () {
    return {

    }
  },
  computed : {
    ...mapState(['isLoginUser'])
  },
  methods : {
    ...mapActions(['setLoginUser','setLogoutUser']),
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
    // kakaoGetAccessToken() {
    //   return window.Kakao.Auth.getAccessToken();
    // },
    kakaoLogin() {
      if (!this.isLoginUser) { // 로그인 상태가 아니라면 로그인을 실행
        window.Kakao.Auth.login({
          scope : 'account_email, gender, age_range',
          success : (authObj) => {
            console.log('authObj',authObj)
            window.Kakao.API.request({
              url:'/v2/user/me',
            })
            .then((res)=>{
              const kakao_account = res.kakao_account;
              const user_id = res.id;
              this.setLoginUser();
              console.log(kakao_account);
              console.log('함수 완료 후',user_id,typeof(user_id));
            })
            .catch((err)=>{
              console.log('err',err);
            })
          },
          fail : (err) => {
            console.log(err)
          }
        });
      }
      else { //로그인 상태에서 로그인 함수를 실행하려한다면 HOME으로 보낸다.
        // this.$router.push({name : "Home"})
      }
    },
    kakaoLogout() {
      if (this.isLoginUser) {
        window.Kakao.Auth.logout(()=>{              
          this.setLogoutUser();
        })
      }
      else {
        console.log('not login user')
      }
    },
    deleteKakaoConnection() { // 카카오, 오후 세시 연결 끊기 === 회원 탈퇴
      window.Kakao.API.request({
        url : '/v1/user/unlink',
      })
      .then((res) => {
        console.log(res)
      })
      .catch((err) => {
        console.log(err)
      })
    }
  },
  mounted () {
    console.log(this.isLoginUser)
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