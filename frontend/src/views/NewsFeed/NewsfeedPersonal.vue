<template>
  <div>
    <div
      class="newsfeed-header animate__animated animate__fadeIn"
      :style="{
        background:
          `linear-gradient(to right, rgba(0, 0, 0, 0.45), rgba(0, 0, 0, 0.45)), url(` +
          headerImg +
          `)`,
      }"
    >
    <div class="newsfeed-header-text">

      <div class="newsfeed-header-title animate__animated animate__fadeIn">
        {{ headerTitle }}
      </div>
      <div class="newsfeed-header-intro animate__animated animate__fadeIn" v-html="headerIntro">
        
      </div>
    </div>
      <NewsFeedHeader :Category="Category" />
    </div>
    <div class="newsfeed-body">
      <Sidebar />
      <div id="newsfeed" class="newsfeed" onscroll="scrollFunction">
        <GroupNav v-if="Category == 2" :isHome="true" />
        <NewsFeedRecommend
          :Category="Category"
        />
        <!-- 임시로 자리차지하기 -->

        <NewsFeedList
          :feed="feed"
          :Category="Category"
          :last="last"
          :next="next"
        />
      </div>
    </div>
  </div>
</template>

<script>
import NewsFeedHeader from "../../components/NewsFeed/NewsFeedHeader.vue";
import NewsFeedList from "../../components/NewsFeed/NewsFeedList.vue";
import NewsFeedRecommend from "../../components/NewsFeed/Recommend/NewsFeedRecommend.vue";
import Sidebar from "../../components/Common/Sidebar.vue";
import { indoorsList } from "@/api/indoors.js";
import { groupList } from "@/api/group.js";
import { outdoorsList } from "@/api/outdoors.js";
import { workerList } from "@/api/worker.js";
import GroupNav from "../../components/GroupFeed/GroupNav.vue";

export default {
  name: "NewsfeedPersonal",
  components: {
    NewsFeedHeader,
    NewsFeedRecommend,
    NewsFeedList,
    Sidebar,
    GroupNav,
  },
  data() {
    return {
      feed: [],
      Category: null,
      height: 0,
      page: 0,
      last: false,
      next: false,
      scrolled: false,
      didScroll: false,
      lastScrollTop: 0,
      delta: 5,
      navbarHeight: 70,
      headerImg:
        "https://cdn.pixabay.com/photo/2017/08/02/01/29/lifestyle-2569543_960_720.jpg",

      headerImgs: [
        require("../../assets/header/indoor.jpg"),
        require("../../assets/header/hackinsider.jpg"),
        require("../../assets/header/chungsan.jpg"),
        require("../../assets/header/work.jpg"),
      ],
      headerTitle: null,
      headerIntro: null
    };
  },
  methods: {
    setFeedList() {
      if (this.$route.query.Category == 1) {
        this.headerTitle = '꽃보다 집'
        this.headerIntro = '내 인생을 사는 당신을 위한 취미 일상 공유 SNS'
        this.Category = 1;
        indoorsList(
          this.page,
          (res) => {
            this.page = res.data.endNum;
            let feeds = res.data.feedList;
            if (feeds && feeds.length < 10) {
              this.last = true;
            }
            for (let f of feeds) {
              this.feed.push(f);
            }
            this.next = false;
          },
          (err) => {
            console.log(err);
          }
        );
      } else if (this.$route.query.Category == 2) {
        this.headerTitle = '핵인싸'
        this.headerIntro = '언제나 모임의 중심이 되는 당신을 위한 그룹 SNS'
        this.Category = 2;
        // 핵인싸 axios Get 무한스크롤 요청보내기
        groupList(
          this.page,
          (res) => {
            this.page = this.page + 1;
            let feeds = res.data.feedList;
            if (feeds && feeds.length < 10) {
              this.last = true;
            }
            for (let f of feeds) {
              this.feed.push(f);
            }
            this.next = false;
          },
          (err) => {
            console.log(err);
          }
        );
      } else if (this.$route.query.Category == 3) {
        this.headerTitle = '청산별곡'
        this.headerIntro = '속세를 떠나고픈 당신을 위한 자연 공유 SNS'
        this.Category = 3;
        // 청산별곡 axios Get 무한스크롤 요청보내기
        outdoorsList(
          this.page,
          (res) => {
            this.page = res.data.endNum;
            let feeds = res.data.feedList;
            if (feeds && feeds.length < 10) {
              this.last = true;
            }
            for (let f of feeds) {
              this.feed.push(f);
            }
            this.next = false;
          },
          (err) => {
            console.log(err);
          }
        );
      } else if (this.$route.query.Category == 4) {
        this.headerTitle = '워커홀릭'
        this.headerIntro = '열정 넘치는 당신을 위한 일자리, 복지 정보 공유 SNS'
        this.Category = 4;
        // 워커홀릭 axios Get 무한스크롤 요청보내기
        workerList(
          this.page,
          (res) => {
            this.page = res.data.endNum;
            let feeds = res.data.feedList;
            if (feeds && feeds.length < 10) {
              this.last = true;
            }
            for (let f of feeds) {
              this.feed.push(f);
            }
            this.next = false;
          },
          (err) => {
            console.log(err);
          }
        );
      }
    },
    setScroll() {
      window.addEventListener("scroll", () => {
        let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
        let windowHeight = window.innerHeight; // 스크린 창
        let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
        this.Interval();
        if (
          !this.next &&
          !this.last &&
          scrollLocation + windowHeight >= fullHeight
        ) {
          this.next = true;
          setTimeout(() => {
            this.setFeedList();
          }, 500);
        }
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
    checkImageNum() {
      if (this.Category === 1) {
        this.headerImg = this.headerImgs[0];
      } else if (this.Category === 2) {
        this.headerImg = this.headerImgs[1];
      } else if (this.Category === 3) {
        this.headerImg = this.headerImgs[2];
      } else if (this.Category === 4) {
        this.headerImg = this.headerImgs[3];
      }
    },
    checkCategory() {

      return new Promise((resolve) => {
        const Category = this.$route.query.Category;
        const CategoryPattern = /^[1-4]$/;
        const patternCheck = CategoryPattern.test(Category);
        resolve(patternCheck)
      })
    }
  },
  created() {
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.setFeedList();
  },
  mounted() {
    this.setScroll();
    this.checkImageNum();
    this.checkCategory()
    .then((res)=>{ 
      if (!res) {
        this.$router.push({name : "NotFound"});
      }
    })
  },
};
</script>

<style></style>
