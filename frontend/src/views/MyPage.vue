<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
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
                  src="http://www.topstarnews.net/news/photo/201903/598316_280802_2053.jpg"
                  alt="프로필사진"
                />
              </div>
              <h1>{{ profileinfo.username }}</h1>
              <div class="myPageInroduce">{{ profileinfo.introduce }}</div>
              <div
                :class="[!mypage ? '' : 'profile_none']"
                class="myPageSubscribe"
              >
                <div
                  :class="{ profielSubscribedNone: subscribed }"
                  class="myPageSubscribed"
                  @click="followToggle"
                >
                  {{ profileinfo.username }}님의 소식 받기
                </div>
                <div
                  :class="{ profielSubscribedNone: !subscribed }"
                  class="myPageSubscribed"
                  @click="followToggle"
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
                    <!-- {{ profileinfo.username }}님이 구독한 사람 -->
                    팔로우
                  </h3>
                  <!-- <h3 :class="{ profile_none: !mypage }">내가 구독한 사람</h3> -->
                  <h3 :class="{ profile_none: !mypage }">팔로우</h3>
                  <div>{{ profileinfo.fromMeToOthersCnt }}</div>
                </div>
                <div class="profileDetailInfo">
                  <h3 :class="{ profile_none: mypage }">
                    <!-- {{ profileinfo.username }}님을 구독한 사람 -->
                    팔로워
                  </h3>
                  <!-- <h3 :class="{ profile_none: !mypage }">나를 구독한 사람</h3> -->
                  <h3 :class="{ profile_none: !mypage }">팔로워</h3>
                  <div>{{ profileinfo.toMeFromOthersCnt }}</div>
                </div>
                <div class="profileDetailInfo">
                  <h3 :class="{ profile_none: mypage }">
                    <!-- {{ profileinfo.username }}님이 가입한 그룹 -->
                    가입한 그룹
                  </h3>
                  <!-- <h3 :class="{ profile_none: !mypage }">내가 가입한 그룹</h3> -->
                  <h3 :class="{ profile_none: !mypage }">가입한 그룹</h3>
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
                  <div v-if="mypage" class="myPageTabLinkContent">
                    <a
                      @click.prevent="activetab = 2"
                      :class="[activetab === 2 ? 'active' : '']"
                      >알림</a
                    >
                    <div
                      :class="[activetab === 2 ? 'active' : '']"
                      class="activeBar"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </header>
        <article class="myPagearticleFrame">
          <div class="myPagearticle">
            <!-- 최근활동 -->
            <section v-if="activetab === 1" class="myPageActivity">
              <div class="myPageActivity-feed-filter-btn">
                <button @click="onClickFeedIndoor">꽃보다집</button>
                <button @click="onClickFeedOutdoor">청산별곡</button>
                <button @click="onClickFeedGroup">핵인싸</button>
                <button @click="onClickFeedWorker">워커홀릭</button>
              </div>
              <NewsFeedList :feed="feedObj[currentFeedName].feed" :last="feedObj[currentFeedName].last" :Category="feedObj[currentFeedName].category" />
            </section>
            <section v-if="activetab === 2" class="myPageActivity">
              <Activity :activities="current_user_activityList" />
            </section>
            <section v-if="activetab === 3" class="myPageActivity">
              <SubscribedList :subscribedlist="current_user_followingList" @decrement="onDeleteSubscriber" />
            </section>
            <section v-if="activetab === 4" class="myPageActivity">
              <GroupList :grouplist="groups" />
            </section>
            <!-- 피드 -->
          </div>
        </article>
      </section>
    </div>
  </div>
</template>

<script>
import Activity from "@/components/MyPage/Activity.vue";
import SubscribedList from "@/components/MyPage/SubscribedList.vue";
import GroupList from "@/components/MyPage/GroupList.vue";
import NewsFeedList from "../components/NewsFeed/NewsFeedList.vue";
import {
  getprofileInfo,  
  getprofileFeedIndoor, 
  getprofileFeedOutdoor,
  getprofileFeedGroup,
  getprofileGroups,
  getprofileFeedWorker,
  followToggle,
  followingList,
} from "@/api/mypage.js";
import Sidebar from "@/components/Common/Sidebar.vue";
import {getNotice} from "@/api/notice.js"
export default {
  components: {
    Sidebar,
    Activity,
    SubscribedList,
    GroupList,
    NewsFeedList,
  },
  data() {
    return {
      name: "명도균",
      current_user: 0,
      profile_user: 0,
      // 해당 페이지 유저를 구독했는지 여부
      subscribed: false,
      // 내 홈페이지 여부
      groups: [],
      groupIds : [],
      mypage: false,
      activetab: 1,
      profileinfo: {
        name: "명도균",
        user_id: 1,
        following: 100,
        follow: 1000,
        group: 4,
      },
      feedObj: {
        indoor : {
          feed_page_no : 0,
          last : false,
          next : false,
          feed : [],
          category : 1,
        },
        outdoor : {
          feed_page_no : 0,
          last : false,
          next : false,
          feed : [],
          category : 3,
        },
        worker : {
          feed_page_no : 0,
          last : false,
          next : false,
          feed : [],
          category : 4,
        },
        group : {
          feed_page_no : 0,
          last : false,
          next : false,
          feed : [],
          category : 2,
        }
      },
      extra : 100,
      currentFeedName : 'indoor',
      current_user_followingList: [],
      current_user_activityList : [],
    };
  },
  methods: {
    onClickFeedIndoor() {
      this.currentFeedName = 'indoor'
      this.resetFeedObj('indoor')
      .then(()=>{
        this.setFeedList('indoor')
      })
    },
    onClickFeedOutdoor() {
      this.currentFeedName = 'outdoor'
      this.resetFeedObj('outdoor')
      .then(()=>{
        this.setFeedList('outdoor')
      })
    },
    onClickFeedGroup() {
      this.currentFeedName = 'group'
      this.resetFeedObj('group')
      .then(()=>{
        this.setFeedList('group')
      })
    },
    onClickFeedWorker() {
      this.currentFeedName = 'worker'
      this.resetFeedObj('worker')
      .then(()=>{
        this.setFeedList('worker')
      })
    },
    resetFeedObj(clickedFeedName) {
      return new Promise((resolve) => {
        const Objkeys= Object.keys(this.feedObj)
        for (let i=0;i<Objkeys.length;i++) {
          const keyName = Objkeys[i]
          if (clickedFeedName !== keyName) {
            this.feedObj[keyName].last = false
            this.feedObj[keyName].next = false
            this.feedObj[keyName].feed = []
            this.feedObj[keyName].feed_page_no = 0
          }
        }
        this.extra = 100
        resolve()
      })  
    },
    setFeedList(curFeedName) {
      switch(curFeedName) {
        case 'indoor':
          this.getIndoorFeed();
          break;
        case 'outdoor':
          this.getOutdoorFeed();
          break;
        case 'worker':
          this.getWorkerFeed();
          break;
        case 'group':
          this.getGroupFeed(this.groupIds);
      }
    },
    getIndoorFeed() {
      getprofileFeedIndoor(
        this.profile_user,
        this.feedObj.indoor.feed_page_no,
        (res) => {
          this.feedObj.indoor.feed_page_no = res.data.endNum;
          let feeds = res.data.feedList;
          if (feeds.length < 10) {
            this.feedObj.indoor.last = true;
          }
          for (let f of feeds) {
            this.feedObj.indoor.feed.push(f);
          }
          this.feedObj.indoor.next = false;
        },
        (err) => {
          console.error(err);
        }
      );
    },
    getOutdoorFeed() {
      getprofileFeedOutdoor(
        this.profile_user,
        this.feedObj.outdoor.feed_page_no,
        (res) => {
          this.feedObj.outdoor.feed_page_no = res.data.endNum;
          let feeds = res.data.feedList;
          if (feeds.length < 10) {
            this.feedObj.outdoor.last = true;
          }
          for (let f of feeds) {
            this.feedObj.outdoor.feed.push(f);
          }
          this.feedObj.outdoor.next = false;
        },
        (err) => {
          console.error(err);
        }
      );
    },
    getWorkerFeed() {
      getprofileFeedWorker(
        this.profile_user,
        this.feedObj.worker.feed_page_no,
        (res) => {
          this.feedObj.worker.feed_page_no = res.data.endNum;
          let feeds = res.data.feedList;
          if (feeds.length < 10) {
            this.feedObj.worker.last = true;
          }
          for (let f of feeds) {
            this.feedObj.worker.feed.push(f);
          }
          this.feedObj.worker.next = false;
        },
        (err) => {
          console.error(err);
        }
      );
    },
    getGroupFeed(groupIds) {
      for (let i=0;i<groupIds.length;i++) {
        const groupId = groupIds[i]
        getprofileFeedGroup(
          this.profile_user,
          groupId,
          0,
          (res) => {
            console.log('getprofileFeedGroup',res.data)
            let feeds = res.data.feedList;
            for (let f of feeds) {
              this.feedObj.group.feed.push(f);
            }
          },
          (err) => {
            console.log('getprofileFeedGroup',err)
          }
        )
      }
    },
    getGroups() {
      return new Promise((resolve,reject) => {
        getprofileGroups(
          this.profile_user,
          (res) => {
            this.groups = res.data
            for (let i=0;i<this.groups.length;i++) {
              const groupId =this.groups[i].id
              this.groupIds.push(groupId)
            }
            resolve(this.groupIds)
          },
          (err) => {
            console.log('getprofileGroups',err)
            reject()
          }
        )
      })
    },
    setScroll() {
      window.addEventListener("scroll", () => {
        let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
        let windowHeight = window.innerHeight; // 스크린 창
        let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
        if (
          !this.feedObj[this.currentFeedName].next &&
          !this.feedObj[this.currentFeedName].last &&
          scrollLocation + windowHeight + this.extra >= fullHeight
        ) {
          this.feedObj[this.currentFeedName].next = true;
          setTimeout(() => {
            this.extra += 100
            this.setFeedList(this.currentFeedName);
          }, 1000);
        }
      });
    },
    usercheck() {
      // console.log(
      //   "같은 유저인지?",
      //   this.$store.state.userId,
      //   this.$route.query.name
      // );
      if (
        this.$route.query.name === undefined ||
        this.$route.query.name === this.$store.state.userId ||
        Number(this.$route.query.name) === this.$store.state.userId
      ) {
        this.current_user = this.$store.state.userId;
        this.profile_user = this.$store.state.userId;
        this.mypage = true;
        // this.current_user = 뷰엑스나 세션에서 아이디 가지고 오기
      } else {
        this.profile_user = this.$route.query.name;
        this.mypage = false;
        // console.log("내 프로필이 아니다", this.profileinfo);
      }
    },
    getprofileInfo() {
      getprofileInfo(
        this.profile_user,
        (res) => {
          // console.log(res);
          this.profileinfo = res.data;
        },
        (err) => {
          console.log(err);
        }
      );
      // this.$router.push({ name: "MyPage", query: { username: "장수민" } });
      // axios 요청보내서 현재 쿼리에 있는 사람 정보 가지고 오는 것
    },
    followToggle() {
      followToggle(
        this.profile_user,
        () => {
          // console.log("팔로우 되는지", res.data);
          if (this.subscribed === false) {
            (this.subscribed = true),
              (this.profileinfo.toMeFromOthersCnt =
                this.profileinfo.toMeFromOthersCnt + 1);
          } else {
            this.subscribed = false;
            this.profileinfo.toMeFromOthersCnt =
              this.profileinfo.toMeFromOthersCnt - 1;
          }
        },
        (err) => {
          console.error(err);
        }
      );
    },
    getfollowingList() {
      followingList(
        this.current_user,
        (res) => {
          // console.log("내가팔로우하는 사람", res.data);
          this.current_user_followingList = res.data;
        },
        (err) => {
          console.error(err);
        }
      );
    },
    getActiviy() {
      getNotice(
        this.current_user,
        (res) => {
          const resArray = []
          // console.log(res.data)
          for (let i=res.data.length-1;i>-1;i--) {
            resArray.push(res.data[i])
          }
          this.current_user_activityList = res.data
        },
        (err) => {
          console.log('err',err)
        }
      )
    },
    onDeleteSubscriber() {
      this.getfollowingList();
      this.getprofileInfo();
    } 
  },
  created() {
    // console.log(this.$store.state.userId);
    this.usercheck();
    this.getprofileInfo();
    this.setFeedList(this.currentFeedName);
  },
  mounted() {
    this.setScroll();
    this.getfollowingList();
    this.getActiviy();
    this.getGroups()
    .then((res) => {
      this.getGroupFeed(res)
    })
  },
};
</script>
