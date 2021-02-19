<template>
  <div>
    <!-- <Nav /> -->
    <div
      :class="[navslider ? 'sliderbar active' : 'sliderbar']"
      @click="navslider = false"
    >
      <div :class="[navslider ? 'sliderFrame active' : 'sliderFrame']">
        <div class="sliderTitle">
          <img style="margin-right: 10px;" src="@/assets/img/logo_ver3.png" width=24px alt="" >
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
      <div style="display:flex; align-items:center;">
        <img style="margin-right: 10px;" src="@/assets/img/logo_ver3.png" width=24px alt="" >
        <span @click="goToHome" style="cursor:pointer; font-weight: 500">오후세시</span>
      </div>
      <span style="width:60px">
        <img
          style="cursor:pointer; margin-right:10px"
          @click="goToSearch"
          src="@/assets/searching.svg"
          alt=""
          width="30%"
          height="80%"
        />
        <!-- <i class="fas fa-bell" style="color:yellow; font-size:18px;"></i> -->
        <img 
          style="cursor:pointer; " 
          src="@/assets/icons/bell_icon.svg" 
          width=20px
          @click="goToNotification"
        />
        
      </span>
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
    goToNotification() {
      this.$router.push({ name: "MyPage", query: { activetab: "2" } }).catch((err) => {
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
          // alert("logout");
          this.$swal.fire({
            icon: 'success',
            text: '로그아웃',
            showConfirmButton: false,
            timer: 1500
          })
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
      window.scrollTo({ top: 0, behavior: "auto" });
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
@media screen and (max-width: 900px) {
  #search {
    font-size: 16px;
  }
  .side-bar {
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
    width: 45px;
    height: 45px;
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
  .autocompleteUl {
    width: 85%;
    margin-left : 15px;
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
  .newsfeed-header-text {
    margin-bottom: 60px;
    margin-right: 30px;
  }
  .newsfeed-header-title {
    font-size: 24px;
    margin-bottom: 10px;
  }
  .newsfeed-header-intro {
    font-size: 12px;
  }
  .newsfeed-body {
    margin-top: 0px;
    padding-top: 0;
  }
  .sliderToggle {
    display: block;
    cursor: pointer;
    z-index: 900;
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
    margin-bottom: 24px;
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
  .edit-btn-container > button {
    padding: 5px;
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
    width: 80px;
    height: 80px;
    margin-top: 12px;
  }
  .editImg-upload-title {
    font-size: 17px;
    font-weight: 500;
  }
  .edit-profieImg-component {
    width: 100%;
  }
  .select-Edit-Img-btn {
    padding: 5px;
  }
  .nickname-warn {
    flex-wrap: wrap;
  }
  .prrfile-content-frame {
    width: 90%;
  }
  .save-Edit-profile {
    width: 250px;
    margin: 12px;
  }
  .profile-intro-frame-title-container {
    width: 90%;
  }
  .newsfeed-form-tag-input {
    border-bottom: 3px solid #f0d3c1;
  }
  .newsfeed-form-img-box label {
    border: 2px dashed #f0d3c1;
  }
  .newsfeed-form-file-hr {
    border-top: 2px solid #f0d3c1;
  }
  .feed-detail-img {
    width: 100%;
  }
  .feed-detail-comment-btn {
    width: 10%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .feed-userprofile-space {
    width: 45px;
    height: 45px;
  }
  .feed-box {

  }
  .feed-content-box {
    padding: 0;
    margin-top:10px;
  }
  .feed-content-box-img-container {
    background-color: rgb(0, 0, 0, 0.8)
  }
  .feed-footer-box {
    padding: 10px
  }
    
}
</style>
