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
      <h1>오후 세시</h1>
      <p>우리의서비스는
제 2의 인생을시작하는 오팔 세대를 위한 라이프 스타일 공유 SNS
      </p>
    </section>
    <section class="home-section">
      <h1>꽃보다 집</h1>
      <p>
        새로운 취미 배우기를 좋아하고 집에서 활동하기를 좋아하는
당신을 위한 카테고리입니다.
배운 취미를 다른 사람들과 공유 하다보면
어느새 장인이 되어 있지 않을까요?
      </p>
    </section>
    <section class="home-section">
      <h1>청산별곡</h1>
      <p>자연과 함께 살고 싶은 로망이 있는 당신을 위한 카테고리 입니다.
곳곳의 아름다운 모습과 낚시, 주말농장 등
사진과 함께 위치를 콕! 찍어 공유해 보는 것은 어떨까요?
또한, 이곳이 궁금하다! 사진 속 아름 다운 명소의 위치를 확인해보세요!
</p>
    </section>
    <section class="home-section">
      <h1>핵인싸</h1>
      <p>다른 사람들과 함께하는 활동을 좋아하는 핵인싸,
당신을 위한 카테고리입니다.
같은 관심사를 가진 사람들과 함께
그룹을 만들어 모일 수 있습니다.
나의 그룹 사람들과 함께
정보, 사진 공유를 해보는 것은 어떨까요?
</p>
    </section>
    <section class="home-section">
      <h1>워커홀릭</h1>
      <p>내 인생에 은퇴란 없다.
일이 최고, 일을포기하지 못한 당신을 위해 준비했습니다.
은퇴 후 경제 활동에 대한 이야기를 나누어 보는 것은 어떨까요?
일자리 정보와 복지 정보가 궁금하다면
사이트로 바로 이동할 수 있습니다.
</p>
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
              console.log('정보', res.data)
              const responseUserId = res.data.id
              const authToken = res.data['accToken']
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