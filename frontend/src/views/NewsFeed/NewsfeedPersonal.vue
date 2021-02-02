<template>
  <div id="newsfeed" class="newsfeed" onscroll="scrollFunction">
    <NewsFeedHeader :Category="Category" class="newsfeed-header" />
    <NewsFeedRecommend :reco="reco" :Category="Category" />
    <NewsFeedList :feed="feed" :Category="Category" :last="last" />
  </div>
</template>

<script>
import NewsFeedHeader from "../../components/NewsFeed/NewsFeedHeader.vue";
import NewsFeedList from "../../components/NewsFeed/NewsFeedList.vue";
import NewsFeedRecommend from "../../components/NewsFeed/NewsFeedRecommend.vue";

import { feedList } from "@/api/feed.js";

export default {
  name: "NewsfeedPersonal",
  components: {
    NewsFeedHeader,
    NewsFeedRecommend,
    NewsFeedList,
  },
  data() {
    return {
      feed: [],
      reco: [
        {
          id: 1,
          content: "첫번째 피드입니다",
          date: "2020-01-29",
          tag: ["테스트", "첫번째"],
          file: `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          user: {
            id: 5,
            nickname: "이병훈",
          },
          like: [
            {
              id: 1,
              user: {
                id: 10,
                nickname: "장수민",
              },
              date: "2021-01-31",
            },
            {
              id: 2,
              user: {
                id: 11,
                nickname: "명도균",
              },
              date: "2021-01-30",
            },
          ],
          comment: [
            {
              id: 1,
              user: {
                id: 11,
                nickname: "명도균",
              },
              date: "2021-01-31",
              content: "훌륭합니다!",
              like: [
                {
                  id: 1,
                  user: {
                    id: 10,
                    nickname: "장수민",
                  },
                  date: "2021-01-31",
                },
              ],
              nested_comment: [
                {
                  id: 1,
                  user: {
                    id: 10,
                    nickname: "장수민",
                  },
                  date: "2021-01-31",
                  content: "대댓글!!",
                  like: [
                    {
                      id: 1,
                      user: {
                        id: 10,
                        nickname: "장수민",
                      },
                      date: "2021-01-31",
                    },
                  ],
                },
              ],
            },
          ],
        },
        {
          id: 2,
          content: "두번째 피드입니다",
          date: "2020-01-29",
          tag: ["테스트", "두번째"],
          file: `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          user: {
            id: 5,
            nickname: "김상원",
          },
          like: [
            {
              id: 1,
              user: {
                id: 10,
                nickname: "장수민",
              },
              date: "2021-01-31",
            },
            {
              id: 2,
              user: {
                id: 11,
                nickname: "명도균",
              },
              date: "2021-01-30",
            },
          ],
          comment: [
            {
              id: 1,
              user: {
                id: 11,
                nickname: "명도균",
              },
              date: "2021-01-31",
              content: "훌륭합니다!",
              like: [
                {
                  id: 1,
                  user: {
                    id: 10,
                    nickname: "장수민",
                  },
                  date: "2021-01-31",
                },
              ],
              nested_comment: [
                {
                  id: 1,
                  user: {
                    id: 10,
                    nickname: "장수민",
                  },
                  date: "2021-01-31",
                  content: "대댓글!!",
                  like: [
                    {
                      id: 1,
                      user: {
                        id: 10,
                        nickname: "장수민",
                      },
                      date: "2021-01-31",
                    },
                  ],
                },
              ],
            },
          ],
        },
        {
          id: 3,
          content: "세번째 피드입니다",
          date: "2020-01-29",
          tag: ["테스트", "세번째"],
          file: ``,
          user: {
            id: 5,
            nickname: "차민석",
          },
          like: [
            {
              id: 1,
              user: {
                id: 10,
                nickname: "장수민",
              },
              date: "2021-01-31",
            },
            {
              id: 2,
              user: {
                id: 11,
                nickname: "명도균",
              },
              date: "2021-01-30",
            },
          ],
          comment: [
            {
              id: 1,
              user: {
                id: 11,
                nickname: "명도균",
              },
              date: "2021-01-31",
              content: "훌륭합니다!",
              like: [
                {
                  id: 1,
                  user: {
                    id: 10,
                    nickname: "장수민",
                  },
                  date: "2021-01-31",
                },
              ],
              nested_comment: [
                {
                  id: 1,
                  user: {
                    id: 10,
                    nickname: "장수민",
                  },
                  date: "2021-01-31",
                  content: "대댓글!!",
                  like: [
                    {
                      id: 1,
                      user: {
                        id: 10,
                        nickname: "장수민",
                      },
                      date: "2021-01-31",
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],
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
          let feeds = res.data.indoorResponseDtoList;
          if (feeds.length < 100) {
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
.newsfeed {
  width: 940px;
  padding: 15px;
  margin: 0 auto;
}
.newsfeed-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.loading {
  display: flex;
  width: 100%;
  padding: auto;
}
.spinner {
  margin: auto;
  animation: spin 1000ms infinite linear;
}
@keyframes spin {
  100% {
    transform: rotate(360deg);
  }
}
</style>
