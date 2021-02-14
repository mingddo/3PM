<template>
  <div>
    <!-- <Nav /> -->
    <div :class="[scrolled ? 'upper-navbar active' : 'upper-navbar']">
      <i class="fas fa-bars"></i>
      <span>오후세시</span>
      <img src="@/assets/searching.svg" alt="" width="10%" height="60%" />
    </div>
    <router-view :key="$route.fullPath" />
  </div>
</template>

<script>
import { mapState } from "vuex";
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
    };
  },
  methods: {
    goToPersonal() {
      this.$router.push({
        name: "NewsfeedPersonal",
        query: { Category: "꽃보다 집" },
      });
    },
    goToProfile() {
      this.$router.push({ name: "MyPage" });
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
  computed: {
    ...mapState(["isLoginUser"]),
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
    margin-top: 30px;
  }
  .myPagemainSection {
    width: 100%;
    left: 0;
    right: 0;
  }
  .searchFrame {
    width: 100%;
    left: 0;
    right: 0;
    margin-top: 30px;
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
}
</style>
