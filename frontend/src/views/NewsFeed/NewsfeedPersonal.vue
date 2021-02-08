<template>
  <div>
    <div>
      <Sidebar/>
    </div>
    <div id="newsfeed" class="newsfeed" onscroll="scrollFunction">
      <NewsFeedHeader :Category="Category" class="newsfeed-header" />
      <!-- <NewsFeedRecommend :reco="reco" :Category="Category" /> -->
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
      Category: this.$route.query.Category,
      height: 0,
      page: 0,
      last: false,
      next: false,
    };
  },
  methods: {
    setFeedList() {
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
