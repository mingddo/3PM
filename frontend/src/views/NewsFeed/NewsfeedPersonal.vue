<template>
  <div>
    <div>
      <Sidebar/>
    </div>
      <div class="newsfeed-header" >
        <NewsFeedHeader :Category="Category" />
      </div>
      <div id="newsfeed" class="newsfeed" onscroll="scrollFunction">
        <!-- <NewsFeedRecommend :reco="reco" :Category="Category" /> -->
        <div>   <!--임시로 자리차지하기-->
          <p style="padding-top : 40px; padding-bottom:40px; text-align :center;">박수를 가장 많이 받은 게시물</p>
          <p style="display:flex;justify-content:center;align-items:center;height:240px;background-color:rgba(0,0,0,0.5)">캐러셀이 있는 장소</p>
        </div>
        <NewsFeedList :feed="feed" :Category="Category" :last="last" :next="next" />
      </div>
  </div>
</template>

<script>
import NewsFeedHeader from "../../components/NewsFeed/NewsFeedHeader.vue";
import NewsFeedList from "../../components/NewsFeed/NewsFeedList.vue";
// import NewsFeedRecommend from "../../components/NewsFeed/NewsFeedRecommend.vue";
import Sidebar from "../../components/Common/Sidebar.vue";
import { feedList } from "@/api/feed.js";

export default {
  name: "NewsfeedPersonal",
  components: {
    NewsFeedHeader,
    // NewsFeedRecommend,
    NewsFeedList,
    Sidebar,
  },
  data() {
    return {
      feed: [],
      reco: [],
      Category: null,
      height: 0,
      page: 0,
      last: false,
      next: false,
    };
  },
  methods: {
    setFeedList() {
      if (this.$route.query.Category == 1) {
        this.Category = 1
        feedList(
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
        this.Category = 2
        // 핵인싸 axios Get 무한스크롤 요청보내기
      } else if (this.$route.query.Category == 3) {
        this.Category = 3
        // 청산별곡 axios Get 무한스크롤 요청보내기
      } else if (this.$route.query.Category == 4) {
        this.Category = 4
        // 워커홀릭 axios Get 무한스크롤 요청보내기
      }
    },
    setScroll() {
      window.addEventListener("scroll", () => {
        let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
        let windowHeight = window.innerHeight; // 스크린 창
        let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

        if (
          !this.next &&
          !this.last &&
          scrollLocation + windowHeight >= fullHeight
        ) {
          this.next = true;
          setTimeout(() => {
            this.setFeedList();
          }, 1000);
        }
      });
    },
  },
  created() {
    this.setFeedList();
  },
  mounted() {
    this.setScroll();
  },
};
</script>

<style>

</style>
