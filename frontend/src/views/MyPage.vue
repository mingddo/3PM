<template>
  <section class="myPagemainSection">
    <header class="myPageHeaderFrame">
      <div class="myPageHeader">
        <!-- 프로필 정보들 -->
        <div class="profileInfo1">
          <!-- 커버사진 및 본인 프로필 사진, 이름 -->
          <div class="profileImgFrame">
            <img
              v-if="profileinfo.user_img"
              class="pofileImg"
              :src="
                `https://dtbqjjy7vxgz8.cloudfront.net/${profileinfo.user_img}`
              "
              alt="프로필사진"
            />
            <img
              v-else
              class="pofileImg"
              :src="profileinfo.user_img"
              alt="프로필사진"
            />
          </div>
          <h1 v-cloak>{{ profileinfo.username }}</h1>
          <div class="myPageInroduce">{{ profileinfo.introduce }}</div>
          <div :class="[!mypage ? '' : 'profile_none']" class="myPageSubscribe">
            <div
              :class="{ profielSubscribedNone: subscribed }"
              class="myPageSubscribed"
            >
              {{ profileinfo.username }}님의 소식 받기
            </div>
            <div
              :class="{ profielSubscribedNone: !subscribed }"
              class="myPageSubscribed"
            >
              {{ profileinfo.username }}님의 소식 끊기
            </div>
          </div>
        </div>

        <div class="profileInfoDetailFrame">
          <div class="profileInfoDetail">
            <!-- 팔로우, 팔로윙, 그룹, ... 프로필 수정하기 버튼 -->
            <div class="profileDetailInfo">
              <h3 :class="{ profile_none: mypage }">
                {{ profileinfo.username }}님이 구독한 사람
              </h3>
              <h3 :class="{ profile_none: !mypage }">내가 구독한 사람</h3>
              <div>{{ profileinfo.fromMeToOthersCnt }}</div>
            </div>
            <div class="profileDetailInfo">
              <h3 :class="{ profile_none: mypage }">
                {{ profileinfo.username }}님을 구독한 사람
              </h3>
              <h3 :class="{ profile_none: !mypage }">나를 구독한 사람</h3>
              <div>{{ profileinfo.toMeFromOthersCnt }}</div>
            </div>
            <div class="profileDetailInfo">
              <h3 :class="{ profile_none: mypage }">
                {{ profileinfo.username }}님이 가입한 그룹
              </h3>
              <h3 :class="{ profile_none: !mypage }">내가 가입한 그룹</h3>
              <div>{{ profileinfo.groupCnt }}</div>
            </div>
          </div>
          <hr class="myPageHeaderhr" />
          <div class="myPageBottom">
            <div class="myPageTabLink">
              <div class="myPageTabLinkContent">
                <a
                  @click.prevent="activetab = 1"
                  :class="[activetab === 1 ? 'active' : '']"
                  >게시물</a
                >
                <div
                  :class="[activetab === 1 ? 'active' : '']"
                  class="activeBar"
                ></div>
              </div>
              <span :class="[mypage ? '' : 'profile_none']">
                <div class="myPageTabLinkContent">
                  <a
                    @click.prevent="activetab = 2"
                    :class="[activetab === 2 ? 'active' : '']"
                    >최근 활동</a
                  >
                  <div
                    :class="[activetab === 2 ? 'active' : '']"
                    class="activeBar"
                  ></div>
                </div>
              </span>

              <div class="myPageTabLinkContent">
                <a
                  @click.prevent="activetab = 3"
                  :class="[activetab === 3 ? 'active' : '']"
                  >구독자</a
                >
                <div
                  :class="[activetab === 3 ? 'active' : '']"
                  class="activeBar"
                ></div>
              </div>
              <div class="myPageTabLinkContent">
                <a
                  @click.prevent="activetab = 4"
                  :class="[activetab === 4 ? 'active' : '']"
                  >그룹</a
                >
                <div
                  :class="[activetab === 4 ? 'active' : '']"
                  class="activeBar"
                ></div>
              </div>
            </div>
            <span :class="[mypage ? '' : 'profile_none']">
              <div class="settings">설정 변경</div>
            </span>
          </div>
        </div>
      </div>
    </header>
    <article class="myPagearticleFrame">
      <div class="myPagearticle">
        <!-- 최근활동 -->
        <section v-if="activetab === 1" class="myPageActivity">
          <NewsFeedList :feed="feed" :last="last" />
        </section>
        <section v-if="activetab === 2" class="myPageActivity">
          <Activity :activities="activities" />
        </section>
        <section v-if="activetab === 3" class="myPageActivity">
          <SubscribedList :subscribedlist="subscribedlist" />
        </section>
        <section v-if="activetab === 4" class="myPageActivity">
          <GroupList :grouplist="grouplist" />
        </section>
        <!-- 피드 -->
      </div>
    </article>
  </section>
</template>

<script>
import Activity from "@/components/MyPage/Activity.vue";
import SubscribedList from "@/components/MyPage/SubscribedList.vue";
import GroupList from "@/components/MyPage/GroupList.vue";
import NewsFeedList from "../components/NewsFeed/NewsFeedList.vue";
import { getprofileInfo, getprofileFeed } from "@/api/mypage.js";

export default {
  components: { Activity, SubscribedList, GroupList, NewsFeedList },
  data() {
    return {
      name: "명도균",
      current_user: 0,
      profile_user: 0,
      // 해당 페이지 유저를 구독했는지 여부
      subscribed: false,
      // 내 홈페이지 여부
      grouplist: [
        {
          groupname: "윈터솔져",
          group_id: 100,
          signupDate: "2020년 12월 1일",
        },
      ],
      activities: [
        {
          username: "명도균",
          dayInfo: "2021년 1월 25일",
          content: "장수민님이 회원님의 게시물에 좋아요를 눌렀습니다.",
          article_id: 30,
        },
        {
          username: "명도균",
          dayInfo: "2021년 1월 11일",
          content: "이병훈님이 회원님의 게시물에 좋아요를 눌렀습니다.",
          article_id: 30,
        },
        {
          username: "명도균",
          dayInfo: "2021년 1월 1일",
          content: "박성호님이 회원님의 게시물에 좋아요를 눌렀습니다.",
          article_id: 30,
        },
      ],
      mypage: false,
      activetab: 1,
      profileinfo: {
        name: "명도균",
        user_id: 1,
        following: 100,
        follow: 1000,
        group: 4,
      },
      feed: [],
      page: 0,
      last: false,
      next: false,
      subscribedlist: [
        {
          username: "장수민",
          user_id: 4,
          profilesrc: "@/assets/loverduck.png",
        },
        {
          username: "이병훈",
          user_id: 5,
          profilesrc: "@/assets/loverduck.png",
        },
        {
          username: "박성호",
          user_id: 8,
          profilesrc: "@/assets/loverduck.png",
        },
        {
          username: "김상원",
          user_id: 31,
          profilesrc: "@/assets/loverduck.png",
        },
      ],
      feed_page_no: 0,
    };
  },
  methods: {
    setFeedList() {
      getprofileFeed(
        this.profile_user,
        this.feed_page_no,
        (res) => {
          this.page = res.data.endNum;
          let feeds = res.data.feedList;
          if (feeds.length < 100) {
            this.last = true;
          }
          for (let f of feeds) {
            this.feed.push(f);
          }
          this.next = false;
        },
        (err) => {
          console.error(err);
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
    usercheck() {
      if (
        this.$route.query.name === undefined ||
        this.$route.query.name === this.$store.state.userId
      ) {
        this.current_user = this.$store.state.userId;
        this.profile_user = this.$store.state.userId;
        this.mypage = true;
        // this.current_user = 뷰엑스나 세션에서 아이디 가지고 오기
      } else {
        this.profile_user = this.$route.query.name;
        this.mypage = false;
        console.log(this.profileinfo);
      }
    },
    getprofileInfo() {
      getprofileInfo(
        this.profile_user,
        (res) => {
          console.log(res);
          this.profileinfo = res.data;
        },
        (err) => {
          console.log(err);
        }
      );
      // this.$router.push({ name: "MyPage", query: { username: "장수민" } });
      // axios 요청보내서 현재 쿼리에 있는 사람 정보 가지고 오는 것
    },
    getFeed() {
      getprofileFeed(
        this.profile_user,
        this.feed_page_no,
        (res) => {
          console.log(res.data);
          this.feed = res.data.indoorResponseDtoList;
        },
        (err) => {
          console.error(err);
        }
      );
    },
  },
  created() {
    console.log(this.$store.state.userId);
    this.usercheck();
    this.getprofileInfo();
    // this.getFeed();
    this.setFeedList();
  },
  mounted() {
    this.setScroll();
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.myPageHeaderFrame {
  width: 100%;
  padding-top: 80px;
  height: auto;
  background-color: #ffffff;
}

.myPageHeader {
  width: 940px;
  margin: auto;
  padding: 15px;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.profileInfo1 {
  width: 90%;
  margin: auto;
  padding: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.myPageInroduce {
  font-size: 16px;
  margin-bottom: 15px;
  font-weight: 400;
}

.myPageSubscribe {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  background-color: #e4e6e9;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.myPageSubscribed {
  width: 300px;
  height: 50px;
  font-size: 18px;
  font-weight: 800;
  display: flex;
  justify-content: center;
  align-items: center;
}

.profielSubscribedNone {
  display: none;
}

.profileInfoDetailFrame {
  width: 90%;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.profileInfoDetail {
  width: 100%;
  margin: auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.profile_none {
  display: none;
}

.profileDetailInfo {
  height: 60px;
  margin: 30px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  cursor: pointer;
}

.myPageHeaderhr {
  width: 100%;
  height: 1px;
  border: none;
  background-color: rgba(0, 0, 0, 0.2);
  box-shadow: 0 0.5px 0.5px rgba(0, 0, 0, 0.2);
}

.myPageBottom {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.myPageTabLink {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.myPageTabLink a {
  width: 100px;
  height: 50px;
  padding: 5px;
  font-size: 18px;
  font-weight: 600;
  border: none;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.myPageTabLinkContent {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.activeBar {
  width: 100px;
  height: 3px;
}
.activeBar.active {
  border-bottom: solid 3px #1876f2;
}

.myPageTabLink a:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

.myPageTabLink a.active {
  color: #1876f2;
}

.settings {
  width: 100px;
  height: 30px;
  font-size: 16px;
  font-weight: 700;
  margin-right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  background-color: #e4e6e9;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.myPagearticleFrame {
  width: 100%;
  height: auto;
  padding: 15px 0;
  background-color: #f0f2f5;
}

.myPagearticle {
  width: 900px;
  margin: auto;
}

.myPageActivity {
  width: 100%;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.myPagemainSection {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.profileImgFrame {
  width: 150px;
  height: 150px;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 70%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.pofileImg {
  width: 200px;
  height: 150px;
  object-fit: cover;
}
</style>
