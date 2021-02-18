<template>
  <div class="side-bar">
    <div class="nav-toggle-switch">
      <div style="padding-left : 15px;">
        <i class="fas fa-bars"></i>
      </div>
    </div>
    <div class="nav-logo">
      카테고리
    </div>
    <div class="nav-toggle-switch">
      <div style="padding-right : 15px;">
        <i class="fas fa-search" @click="goToSearch"></i>
      </div>
    </div>
    <div class="nav-content" v-if="userStatus">
      <div class="nav-categoryFrame" @click="goToPersonal">
        <div>
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
      <div class="nav-categoryFrame" @click="goToHack">
        <div>
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
      <div class="nav-categoryFrame" @click="goToChungSan">
        <div>
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
      <div class="nav-categoryFrame" @click="goToWH">
        <div>
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
      <div class="nav-categoryFrame" @click="goToSearch">
        <div>
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
      <div class="nav-categoryFrame" @click="goToProfile">
        <div>
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
      <div class="nav-categoryFrame" @click="goToSetting">
        설정 변경
      </div>
      <div class="nav-categoryFrame" @click="goToLogout">
        로그아웃
      </div>
    </div>

    <div class="overlay">
      <div class="nav-toggle-bar-before">
        <div class="nav-logo">
          <i class="far fa-clock"></i>
          카테고리이름
        </div>
        <div @click="onClickSidebarPersonal">
          꽃보다 집
        </div>
        <div @click="onClickSidebarChungSan">
          청산별곡
        </div>
        <div @click="onClickSidebarSearch">
          검색
        </div>
        <div @click="onClickSidebarProfile">
          프로필
        </div>
        <div @click="onClickSidebarSetting">
          설정 변경
        </div>
        <div @click="onClickSidebarLogout">
          로그아웃
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "Nav",
  data() {
    return {
      toggleStatus: false,
    };
  },
  created() {},
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
    goToHome() {
      this.$router.push({ name: "Home" }).catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      });
    },
    goToSearch() {
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
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "1" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToHack() {
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "2" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToChungSan() {
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "3" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToWH() {
      this.$router
        .push({ name: "NewsfeedPersonal", query: { Category: "4" } })
        .catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
    },
    goToProfile() {
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
      this.kakaoLogout().then(() => {
        this.$router.push({ name: "Home" });
      });
    },
    onClickSidebarPersonal(e) {
      this.sideBarToggle(e);
      setTimeout(() => {
        this.$router
          .push({ name: "NewsfeedPersonal", query: { Category: "1" } })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      }, 10);
    },
    onClickSidebarChungSan(e) {
      this.sideBarToggle(e);
      setTimeout(() => {
        this.$router
          .push({ name: "NewsfeedPersonal", query: { Category: "2" } })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      }, 10);
    },
    onClickSidebarHome(e) {
      this.sideBarToggle(e);
      setTimeout(() => {
        this.$router.push({ name: "Home" }).catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
      }, 10);
    },
    onClickSidebarSearch(e) {
      this.sideBarToggle(e);
      setTimeout(() => {
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
      }, 10);
    },
    onClickSidebarProfile(e) {
      this.sideBarToggle(e);
      setTimeout(() => {
        this.$router.push({ name: "MyPage" }).catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
      }, 10);
    },
    onClickSidebarSetting(e) {
      this.sideBarToggle(e);
      setTimeout(() => {
        // alert("setting 창으로 가기");
        this.$swal.fire({
          icon: 'success',
          text: 'Setting 으로 가기',
          showConfirmButton: false,
          timer: 1500
        })
        location.reload();
      }, 10);
    },
    onClickSidebarLogout() {
      this.kakaoLogout().then(() => {
        this.$router.push({ name: "Home" }).catch((err) => {
          if (err.name === "NavigationDuplicated") {
            location.reload();
          }
        });
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
            text: 'Logout',
            showConfirmButton: false,
            timer: 1500
          })
          resolve();
        });
      });
    },
  },
  computed: {
    ...mapState(["userStatus", "kakaoId", "userId", "authToken"]),
  },
};
</script>

<style></style>
