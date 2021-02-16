<template>
  <div>
    <!-- <Nav /> -->
    <div
      :class="[navslider ? 'sliderbar active' : 'sliderbar']"
      @click="navslider = false"
    >
      <div :class="[navslider ? 'sliderFrame active' : 'sliderFrame']">
        <div class="sliderTitle">
          오후 세시
        </div>
        <div class="slider-nav-content">
          <div class="slider-nav-categoryFrame" @click="goToPersonal">
            <div class="slider-nav-categoryFrame-d">
              <img
                src="@/assets/img/indoor.svg"
                alt=""
                width="30%"
                height="100%"
                style="margin-right: 10px"
              />
              <span>
                꽃보다 집
              </span>
            </div>
          </div>
          <div class="slider-nav-categoryFrame" @click="goToHack">
            <div class="slider-nav-categoryFrame-d">
              <img
                src="@/assets/img/hackinsider.svg"
                alt=""
                width="30%"
                height="100%"
                style="margin-right: 10px"
              />
              <span>
                핵인싸
              </span>
            </div>
          </div>
          <div class="slider-nav-categoryFrame" @click="goToChungSan">
            <div class="slider-nav-categoryFrame-d">
              <img
                src="@/assets/img/chungsan2.svg"
                alt=""
                width="30%"
                height="100%"
                style="margin-right: 10px"
              />
              <span>
                청산별곡
              </span>
            </div>
          </div>
          <div class="slider-nav-categoryFrame" @click="goToWH">
            <div class="slider-nav-categoryFrame-d">
              <img
                src="@/assets/img/workerholic2.svg"
                alt=""
                width="30%"
                height="100%"
                style="margin-right: 10px"
              />
              <span>
                워커홀릭
              </span>
            </div>
          </div>
          <div class="slider-nav-categoryFrame" @click="goToSearch">
            <div class="slider-nav-categoryFrame-d">
              <img
                src="@/assets/searching.svg"
                alt=""
                width="20%"
                height="100%"
                style="margin-right: 10px"
              />
              <span>
                검색
              </span>
            </div>
          </div>
          <div class="slider-nav-categoryFrame" @click="goToProfile">
            <div class="slider-nav-categoryFrame-d">
              <img
                src="@/assets/img/profileM.svg"
                alt=""
                width="20%"
                height="100%"
                style="margin-right: 10px"
              />
              <span>
                프로필
              </span>
            </div>
          </div>
          <div class="slider-nav-categoryFrame" @click="goToSetting">
            설정 변경
          </div>
          <div class="slider-nav-categoryFrame" @click="goToLogout">
            로그아웃
          </div>
        </div>
      </div>
    </div>
    <div :class="[scrolled ? 'upper-navbar active' : 'upper-navbar']">
      <i
        @click="toggleslidernavbar"
        class="fas fa-bars"
        :class="[userStatus ? 'sliderToggle' : 'sliderToggle active']"
      ></i>
      <span>오후세시</span>
      <img src="@/assets/searching.svg" alt="" width="10%" height="80%" />
    </div>

    <router-view :key="$route.fullPath" />
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import Nav from './components/Common/Nav.vue'
import "@/assets/css/main.css";
import "@/assets/css/newsfeed.css";
import "@/assets/css/mypage.css";
import "@/assets/css/search.css";

export default {
  components: {},
  data() {
    return {
      scrolled: false,
      didScroll: false,
      lastScrollTop: 0,
      delta: 5,
      navbarHeight: 70,
      navslider: false,
    };
  },
  methods: {
    ...mapActions(["setUserStatus", "setAuthToken", "setKakaoId", "setUserId"]),
    sideBarToggle(e) {
      const toggleBar = document.querySelector(".nav-toggle-bar-before");
      const overlay = document.querySelector(".overlay");
      if (overlay.classList.contains("disappear")) {
        // 사이드바 사라지기
        if (
          e.target.classList.value.split(" ").includes("sidebar-fas") ||
          e.target === overlay
        ) {
          overlay.classList.remove("disappear");
          overlay.style.cssText =
            "display: block;background-color: rgba(0,0,0,0);";
          toggleBar.style = "transform :translateX(0px);";
          setTimeout(() => {
            overlay.style = "display: none;";
          }, 500);
        }
      } else {
        // 사이드바 나타나기
        overlay.classList.add("disappear");
        overlay.style.cssText =
          "display: block;background-color: rgba(0,0,0,0.5);";
        setTimeout(() => {
          toggleBar.style = "transform : translateX(250px);";
        }, 10);
      }
    },
    toggleslidernavbar() {
      console.log("토글된다");
      this.navslider = !this.navslider;
    },
    goToHome() {
      this.navslider = false;
      this.$router.push({ name: "Home" }).catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      });
    },
    goToSearch() {
      this.navslider = false;
      this.$router
        .push({
          name: "Search",
          params: { filter: "all" },
          query: { query: "" },
        })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToPersonal() {
      this.navslider = false;
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "1" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToHack() {
      this.navslider = false;
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "2" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToChungSan() {
      this.navslider = false;
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "3" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToWH() {
      this.navslider = false;
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "4" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToProfile() {
      this.navslider = false;
      this.$router.push({ name: "MyPage" }).catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      });
    },
    goToSetting() {
      this.navslider = false;
      this.$router.push({ name: "MyPageEdit" }).catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      });
    },
    goToLogout() {
      console.log("로그아웃");
      this.navslider = false;
      this.kakaoLogout().then(() => {
        this.$router.push({ name: "Home" });
      });
    },
    kakaoLogout() {
      return new Promise((resolve) => {
        window.Kakao.Auth.logout(() => {
          this.setUserId(null);
          this.setAuthToken(null);
          this.setKakaoId(null);
          this.setUserStatus(null);
          alert("logout");
          resolve();
        });
      });
    },
    setScroll() {
      window.addEventListener("scroll", () => {
        this.Interval();
      });
    },
    Interval() {
      this.didScroll = true;
      setInterval(() => {
        if (this.didScroll) {
          this.hasScrolled();
          this.didScroll = false;
        }
      }, 250);
    },
    hasScrolled() {
      let st = window.scrollY;
      if (Math.abs(this.lastScrollTop - st) <= this.delta) {
        return;
      }
      if (st > this.lastScrollTop && st > this.navbarHeight) {
        this.scrolled = true;
      } else {
        if (st + window.innerHeight < document.body.scrollHeight) {
          this.scrolled = false;
        }
      }
      this.lastScrollTop = st;
    },
  },
  watch: {
    $route() {
      this.scrolled = false;
      console.log("스크롤 탑 함수");
      window.scrollTo({ top: 0, behavior: "auto" });
    },
    userStatus: function() {
      console.log(this.userStatus);
    },
  },
  computed: {
    ...mapState([
      "isLoginUser",
      "userStatus",
      "kakaoId",
      "userId",
      "authToken",
    ]),
  },
  mounted() {
    this.setScroll();
  },
};
</script>
<style>
@media screen and (max-width: 1050px) {
  #search {
    font-size: 16px;
  }
  .side-bar {
    /* position: absolute;
    top: 0px;
    justify-content: space-between;
    width: 100%;
    height: 50px;
    flex-direction: row;
    padding: 0;
    border-radius: 0;
    z-index: 1500; */
    display: none;
  }
  .nav-content {
    display: none;
  }
  .nav-logo {
    padding-top: 0px;
  }
  .newsfeed {
    width: 100%;
    left: 0;
    right: 0;
    margin-top: 50px;
  }
  .newsfeed-D {
    width: 100%;
    margin-top: 80px;
  }
  .myPagemainSection {
    width: 100%;
    left: 0;
    right: 0;
    margin-top: 50px;
  }
  .profileImgFrame {
    width: 90px;
    height: 90px;
  }
  .searchFrame {
    width: 100%;
    left: 0;
    right: 0;
    margin-top: 70px;
  }
  .search_input {
    height: 40px;
  }
  .seartchFilgerHeader {
    font-size: 20px;
  }
  .searchFilter {
    height: 35px;
  }
  .searchFilterTilte {
    font-size: 16px;
  }
  .Groups-resultCategoryTitle {
    margin-top: 10px;
  }
  .filterGroupDetailTitle {
    font-size: 16px;
  }
  .newsfeed-header {
    width: 100%;
    left: 0;
    right: 0;
  }
  .nav-toggle-switch {
    display: block;
  }
  .home-section {
    flex-direction: column;
  }
  .home-section-reverse {
    flex-direction: column-reverse;
  }
  .newsfeed-header {
    height: 200px;
    margin-top: 50px;
  }
  .newsfeed-body {
    margin-top: 0px;

    padding-top: 0;
  }
  .sliderToggle {
    display: block;
    cursor: pointer;
    z-index: 17005;
  }
  .sliderToggle.active {
    display: none;
  }
  .upper-navbar {
    height: 50px;
  }
  .upper-navbar.active {
    top: 0;
  }
  .create-feed-btn {
    height: 40px;
    background-color: #f0d3c1;
    width: 150px;
    border-radius: 5px;
  }
  .newsfeed-form-profile-img-space {
    width: 45px;
    height: 45px;
  }
  .newsfeed-form-profile-img {
    width: 50px;
  }
  .newsfeed-form-profile-name {
    font-size: 16px;
  }
  .newsfeed-form-tag-input {
    font-size: 15px;
  }
  .splide__arrow {
    width: 2em;
    height: 2em;
  }
  .splide__arrow svg {
    width: 1.5em;
    height: 1.5em;
  }
  .home-category-modal-overay {
    display: none;
  }
  .home-category-modal-btn {
    display: none;
  }
  .myPageEdit-Header-Title {
    font-size: 18px;
    font-weight: 700;
    margin: 12px;
  }
  .mypageEdit {
    margin: 80px auto;
    width: 90%;
  }
  .nickname-edit-input-frame {
    width: 80%;
  }
  .nickname-edit-input-frame-btn {
    width: 80%;
  }
  .nickname-edit-input-frame > input {
    width: 60%;
  }
  .introEdit-frame > input {
    width: 80%;
    font-size: 15px;
  }
  .intro-Edit-btn {
    font-size: 13px;
    width: 60px;
  }

  .edit-dupli-btn {
    font-size: 13px;
    padding: 5px;
  }
  .nicknameexist {
    width: 90%;
  }
  .edit-profile-Img-btn {
    padding: 10px;
    font-size: 13px;
  }
  .nicknameEdit-btn {
    font-size: 13px;
    height: 70%;
  }
  .profile-edit-img-frame {
    width: 30%;
    height: 30%;
    margin-top: 12px;
  }
  .nickname-warn {
    flex-wrap: wrap;
  }
  .prrfile-content-frame {
    width: 90%;
  }
  .save-Edit-profile {
    width: 250px;
  }
}
</style>
