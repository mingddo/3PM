<template>
  <div>
    <div class="home">
      <div v-if="!userStatus">
        <button class="login-btn" @click="onClickLogin">
          <i class="fas fa-comment"></i> 카카오로그인
        </button>
      </div>
      <div v-else>
          <div class="home-category-modal-btn">
            <button  @click="onClickCategoryToggle">카테고리 버튼</button>
          </div>
        <div class="home-category-modal-overay" :style="{ display : modalDisplay}">
          <div class="home-category-modal-content-frame">
            <div class="home-category-modal-content" @mouseover="mouseHover.indoor = true" @mouseleave="mouseHover.indoor = false" @click="onClickCategory(1)">
              <div>
                 <img src="../assets/img/indoor.svg" alt="" />
              </div>
              <div class="home-category-modal-content-hover-background">
                <div class="home-category-modal-content-hover-background-header">꽃보다집</div>
              </div>
            </div>
            <div class="home-category-modal-content" @mouseover="mouseHover.group = true" @mouseleave="mouseHover.group = false" @click="onClickCategory(2)">
              <div>
                <img src="../assets/img/hackinsider.svg" alt="" />
              </div>
              <div class="home-category-modal-content-hover-background">
                <div class="home-category-modal-content-hover-background-header">핵인싸</div>
              </div>
            </div>
            <div class="home-category-modal-content" @mouseover="mouseHover.outdoor = true" @mouseleave="mouseHover.outdoor = false" @click="onClickCategory(3)">
              <div>
                <img src="../assets/img/chungsan2.svg" alt="" />
              </div>
              <div class="home-category-modal-content-hover-background">
                <div class="home-category-modal-content-hover-background-header">청산별곡</div>
              </div>
            </div>
            <div class="home-category-modal-content" @mouseover="mouseHover.worker = true" @mouseleave="mouseHover.worker = false" @click="onClickCategory(4)">
              <div>
                <img src="../assets/img/workerholic2.svg" alt="" />
              </div>
              <div class="home-category-modal-content-hover-background">
                <div class="home-category-modal-content-hover-background-header">워커홀릭</div>
              </div>
            </div>
          </div>
        </div>
        <button class="login-btn" style="z-index:1000" @click="onClickLogout">
          로그아웃
        </button>

      </div>
      <!-- <button class="login-btn" @click="deleteKakaoConnection">회원연결끊기</button> -->
      <section class="clock-container">
        <div class="clock-box">
          <div class="clock">
            <div class="clock-dot"></div>
            <div class="hour">
              <div class="hr" id="hr"></div>
            </div>
            <div class="min">
              <div class="mn" id="mn"></div>
            </div>
            <div class="sec">
              <div class="sc" id="sc"></div>
            </div>
          </div>
        </div>
      </section>
      <section class="home-section home-section-before">
        <div class="home-section-img">
          <img src="../assets/img/hackinsider.svg" alt="" />
        </div>
        <div class="home-section-text">
          <div class="home-section-title">
            <img style="margin-right: 10px;" src="@/assets/img/logo_ver3.png" width=24px alt="" >
            오후 세시
          </div>
          <div class="home-section-content">
            우리의 서비스는 제 2의 인생을시작하는 오팔 세대를 위한 라이프 스타일
            공유 SNS입니다
          </div>
        </div>
      </section>
      <section class="home-section-reverse home-section-before">
        <div class="home-section-text">
          <div class="home-section-title">
            꽃보다 집
          </div>
          <div class="home-section-content">
            새로운 취미 배우기를 좋아하고 집에서 활동하기를 좋아하는
            당신을 위한 카테고리입니다.
            배운 취미를 다른 사람들과 공유 하다보면
            어느새 장인이 되어 있지 않을까요?
          </div>
        </div>
        <div class="home-section-img">
          <img src="../assets/img/indoor.svg" alt="" />
        </div>
      </section>
      <section class="home-section home-section-before">
        <div class="home-section-img">
          <img src="../assets/img/chungsan2.svg" alt="" />
        </div>
        <div class="home-section-text">
          <div class="home-section-title">
            청산별곡
          </div>
          <div class="home-section-content">
            자연과 함께 살고 싶은 로망이 있는 당신을 위한 카테고리 입니다.
            곳곳의 아름다운 모습과 낚시, 주말농장 등 사진과 함께 위치를 콕! 찍어
            공유해 보는 것은 어떨까요? 또한, 이곳이 궁금하다! 사진 속 아름 다운
            명소의 위치를 확인해보세요!
          </div>
        </div>
      </section>
      <section class="home-section-reverse home-section-before">
        <div class="home-section-text">
          <div class="home-section-title">
            핵인싸
          </div>
          <div class="home-section-content">
            다른 사람들과 함께하는 활동을 좋아하는 핵인싸, 당신을 위한
            카테고리입니다. 같은 관심사를 가진 사람들과 함께 그룹을 만들어 모일
            수 있습니다. 나의 그룹 사람들과 함께 정보, 사진 공유를 해보는 것은
            어떨까요?
          </div>
        </div>
        <div class="home-section-img">
          <img src="../assets/img/hackinsider.svg" alt="" />
        </div>
      </section>
      <section class="home-section home-section-before">
        <div class="home-section-img">
          <img src="../assets/img/workerholic2.svg" alt="" />
        </div>
        <div class="home-section-text">
          <div class="home-section-title">
            워커홀릭
          </div>
          <div class="home-section-content">
            내 인생에 은퇴란 없다. 일이 최고, 일을포기하지 못한 당신을 위해
            준비했습니다. 은퇴 후 경제 활동에 대한 이야기를 나누어 보는 것은
            어떨까요? 일자리 정보와 복지 정보가 궁금하다면 사이트로 바로 이동할
            수 있습니다.
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import { getUser } from "@/api/login.js";

export default {
  name: "Home",
  data() {
    return {
      hr: "",
      mn: "",
      sc: "",
      clockDot: "",
      whiteSection: "",
      windowInnerHeight: "",
      hh: "",
      mm: "",
      ss: "",
      clockBox: "",
      sections: "",
      categoryToggle : false,
      modalDisplay : null,
    };
  },
  computed: {
    ...mapState([
      "userStatus",
      "kakaoId",
      "userId",
      "authToken",
      "userInfo",
      "refToken",
    ]),
  },
  methods: {
    ...mapActions([
      "setUserStatus",
      "setAuthToken",
      "setRefToken",
      "setKakaoId",
      "setUserId",
      "setUserInfo",
    ]),
    mouseHover(category,status) {
      // status === 1 mouse hover
      // status === 0 mouse out
      console.log(category,status)
      switch(category) {
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
      }
    },
    mouseEnter(e) {
      console.log('mouseEnter',e)
    },
    mouseOut(e) {
      console.log('mouseOut',e)
    },
    onClickCategory(e) {
        this.$router.push({
        name: "NewsfeedPersonal",
        query: { Category: `${e}` },
      });
    },
    onClickCategoryToggle() {
      const overlay = document.querySelector('.home-category-modal-overay')
      this.categoryToggle = !this.categoryToggle;
      if(this.categoryToggle) {
        overlay.style = " opacity : 0"
        setTimeout(() => {
          this.modalDisplay = 'none';
        }, 500);
      }
      else {
        this.modalDisplay = null
        setTimeout(() => {
          overlay.style = " opacity : 1"
        }, 5);
      }
    },
    scrollStatus() {
      // let viewportHeight = window.innerHeight;
      let scrollPos = window.scrollY / this.windowInnerHeight;
      if (scrollPos > 1.5) {
        this.loginBtn.classList.add("login-btn-moved");
      } else {
        this.loginBtn.classList.remove("login-btn-moved");
      }
    },
    kakaoLogin() {
      return new Promise((resolve, reject) => {
        window.Kakao.Auth.login({
          scope: "account_email, gender, age_range",
          success: () => {
            // console.log("kakaoLogin", authObj);
            this.kakaoAccoutInfo();
            resolve();
          },
          fail: (err) => {
            alert(err);
            reject();
          },
        });
      });
    },
    kakaoAccoutInfo() {
      window.Kakao.API.request({
        url: "/v2/user/me",
      })
        .then((res) => {
          const kakaoId = res.id;
          // console.log("kakaoAccoutInfo 함수", res);
          const user_age = res.kakao_account.age_range;
          getUser(
            {
              kakaoId: kakaoId,
            },
            (res) => {
              this.setKakaoId(kakaoId);
              // true -> user 정보가 있으면  Home
              if (res.data) {
                // 세션에 토큰 설정
                // console.log("getUser 정보", res.data);
                const responseUserId = res.data.id;
                const authToken = res.data["accToken"];
                const refToken = res.data["refToken"];
                this.setUserId(responseUserId);
                this.setAuthToken(authToken);
                this.setRefToken(refToken);
                this.setUserStatus(true);
                this.setUserInfo(user_age);
                                
                this.$swal.fire({
                  icon: 'success',
                  text: '로그인 완료',
                  showConfirmButton: false,
                  timer: 1500
                })
                
              }
              // false -> user 정보가 없으면 Signup
              else {
                // 세션에 카카오 ID 설정
                this.$router.push({ name: "Signup" });
              }
            },
            (err) => {
              console.log(err);
            }
          );
        })
        .catch((err) => {
          console.log("err", err);
        });
    },

    kakaoGetAccessToken() {
      return window.Kakao.Auth.getAccessToken();
    },

    kakaoLogout() {
      window.Kakao.Auth.logout(() => {
        this.setUserId(null);
        this.setAuthToken(null);
        this.setKakaoId(null);
        this.setUserStatus(null);
        
        this.$swal.fire({
          icon: 'success',
          text: '로그아웃',
          showConfirmButton: false,
          timer: 1500
        })
      
      });
    },

    deleteKakaoConnection() {
      // 카카오, 오후 세시 연결 끊기 === 회원 탈퇴
      window.Kakao.API.request({
        url: "/v1/user/unlink",
      })
        .then(() => {
          localStorage.clear();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    onClickLogin() {
      this.kakaoLogin()
        .then(() => {          
          this.$router.push({name: "Home"}).catch((err) => {
            if (err.name !== "NavigationDuplicated") {
              console.log(err)
            }
          })
        })
        .catch(() => {
          console.log("error");
        });
    },
    onClickLogout() {
      this.kakaoLogout();
    },
    goToPersonal() {
      this.$router.push({ name: "NewsfeedPersonal", query: { Category: "1" } });
    },
    // onClickDeleteUserConnection() {
    //   deleteKakaoConnection();
    // }
    clockTicTok() {
      const scrollPos = window.scrollY / this.windowInnerHeight;
      switch (true) {
        case scrollPos < 0.25:
          this.ss = 55 * 6;
          this.mm = 59 * 6;
          this.clockBox.classList.remove("clock-box-after");
          break;
        case scrollPos < 0.5:
          this.ss = 56 * 6;
          this.mm = 59 * 6;
          this.clockBox.classList.remove("clock-box-after");
          break;
        case scrollPos < 0.75:
          this.ss = 57 * 6;
          this.mm = 59 * 6;
          this.clockBox.classList.remove("clock-box-after");
          break;
        case scrollPos < 1:
          this.ss = 58 * 6;
          this.mm = 59 * 6;
          this.clockBox.classList.remove("clock-box-after");
          break;
        case scrollPos < 1.25:
          this.ss = 59 * 6;
          this.mm = 59 * 6;
          this.clockBox.classList.remove("clock-box-after");
          break;
        case scrollPos < 1.5:
          this.ss = 60 * 6;
          this.mm = 60 * 6;
          this.clockBox.classList.remove("clock-box-after");
          break;
        default:
          this.clockBox.classList.add("clock-box-after");
      }
      this.hr.style.transform = `rotateZ(${this.hh + this.mm / 12}deg)`;
      this.mn.style.transform = `rotateZ(${this.mm}deg)`;
      this.sc.style.transform = `rotateZ(${this.ss}deg)`;
    },
  },
  mounted() {
    this.hr = document.querySelector("#hr");
    this.mn = document.querySelector("#mn");
    this.sc = document.querySelector("#sc");
    this.clockDot = document.querySelector(".clock-dot");
    this.sections = document.querySelectorAll("section");
    this.whiteSection = document.querySelector("#white-space");
    this.windowInnerHeight = window.innerHeight;
    this.clockBox = document.querySelector(".clock-box");
    this.sections = document.querySelectorAll("section");
    this.hh = 2 * 30;
    this.mm = 59 * 6;
    this.ss = 54 * 6;
    if (!this.isLoginUser) {
      this.loginBtn = document.querySelector(".login-btn");
      this.loginBtnText = document.querySelector(".login-btn-text");
      window.addEventListener("scroll", this.scrollStatus);

      this.hr.style.transform = `rotateZ(${this.hh + this.mm / 12}deg)`;
      this.mn.style.transform = `rotateZ(${this.mm}deg)`;
      this.sc.style.transform = `rotateZ(${this.ss}deg)`;
      window.addEventListener("scroll", this.clockTicTok);
      this.sections.forEach((section, index) => {
        if (index === 0) return;
        window.addEventListener("scroll", function() {
          const scrollPos = window.scrollY / window.innerHeight;
          if (scrollPos < index + 1) {
            section.classList.add("home-section-before");
          } else if (scrollPos > index + 1 && scrollPos < index + 2) {
            section.classList.remove("home-section-before");
            section.classList.remove("home-section-after");
          } else {
            section.classList.add("home-section-after");
          }
        });
      });
    }
  },
};
</script>

<style scoped></style>
