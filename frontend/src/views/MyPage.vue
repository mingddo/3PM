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
                  src="https://dtbqjjy7vxgz8.cloudfront.net/default.gif"
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
                  @click="onClickFollowToggle"
                >
                  {{ profileinfo.username }}님의 소식 받기
                </div>
                <div
                  :class="{ profielSubscribedNone: !subscribed }"
                  class="myPageSubscribed"
                  @click="onClickFollowToggle"
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
                    구독중
                  </h3>
                  <!-- <h3 :class="{ profile_none: !mypage }">내가 구독한 사람</h3> -->
                  <h3 :class="{ profile_none: !mypage }">구독중</h3>
                  <div>{{ profileinfo.fromMeToOthersCnt }}</div>
                </div>
                <div class="profileDetailInfo">
                  <h3 :class="{ profile_none: mypage }">
                    <!-- {{ profileinfo.username }}님을 구독한 사람 -->
                    구독자
                  </h3>
                  <!-- <h3 :class="{ profile_none: !mypage }">나를 구독한 사람</h3> -->
                  <h3 :class="{ profile_none: !mypage }">구독자</h3>
                  <div style="cursor:pointer;" @click="getFollower">{{ profileinfo.toMeFromOthersCnt }}</div>
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
                      >구독중</a
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
          <UserList @closeList="closeList" v-if="openFollower" :type="4" :users="current_user_followerList"/>
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
              <Activity :activities="current_user_activity.list" :activitiesLast="current_user_activity.last" />
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
  followerList
} from "@/api/mypage.js";
import Sidebar from "@/components/Common/Sidebar.vue";
import {getNotice} from "@/api/notice.js"
import UserList from '../components/NewsFeed/Common/UserList.vue';

export default {
  components: {
    Sidebar,
    Activity,
    SubscribedList,
    GroupList,
    NewsFeedList,
    UserList,
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
      current_user_followerList: [],
      current_user_activity : {
        last : false,
        next : false,
        page_no : 0,
        list : [],
        extra : 100,
      },
      openFollower: false,
    };
  },
  methods: {
    closeList () {
      this.openFollower = false;
    },
    getFollower () {
      this.openFollower = true;
    },
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
        this.current_user_activity.extra = 100
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
          this.getGroupFeed();
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
    getGroupFeed() {
        getprofileFeedGroup(
          this.profile_user,
          this.feedObj.group.feed_page_no,
          (res) => {
            this.feedObj.group.feed_page_no = res.data.endNum;
            let feeds = res.data.feedList;
            if (feeds.length < 10) {
              this.feedObj.group.last = true;
            }
            for (let f of feeds) {
              this.feedObj.group.feed.push(f);
            }
            this.feedObj.group.next = false;
          },
          (err) => {
            console.log('getprofileFeedGroup',err)
          }
        )
    },
    getGroups() {
        getprofileGroups(
          this.profile_user,
          (res) => {
            this.groups = res.data
          },
          (err) => {
            console.log(err)
          }
        )
    },
    setActivityScroll() {
      window.addEventListener("scroll", () => {
        if(this.activetab !==2) {return}
        let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
        let windowHeight = window.innerHeight; // 스크린 창
        let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
        if (
          !this.current_user_activity.next &&
          !this.current_user_activity.last &&
          scrollLocation + windowHeight + this.current_user_activity.extra >= fullHeight
        ) {
          this.current_user_activity.next = true;
          setTimeout(() => {
            this.current_user_activity.extra += 100
            this.getActiviy();
          }, 1000);
        }
      });
    },
    setScroll() {
      window.addEventListener("scroll", () => {
        if(this.activetab !==1) {return}
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
      if (
        this.$route.query.name === undefined ||
        this.$route.query.name === this.$store.state.userId ||
        Number(this.$route.query.name) === this.$store.state.userId
      ) {
        this.current_user = this.$store.state.userId;
        this.profile_user = this.$store.state.userId;
        this.mypage = true;
      } else {
        this.profile_user = this.$route.query.name;
        this.mypage = false;
      }
    },
    getprofileInfo() {
      getprofileInfo(
        this.profile_user,
        (res) => {
          this.profileinfo = res.data;
        },
        (err) => {
          console.log(err);
        }
      );
    },
    onClickFollowToggle() {
      if (this.subscribed === true) {
        this.$swal.fire({
          icon: 'warning',
          text: '구독을 취소하시겠어요?',
          showCancelButton: true,
          confirmButtonText: '취소하기', cancelButtonText: '돌아가기'
        }).then((result) => {         
          if (result.isConfirmed) {
            this.$swal.fire({
              icon: 'success',
              text: '구독 취소가 완료되었습니다',
              showConfirmButton: false,
              timer: 1500
            })
            this.followToggle()
          } 
        })

      }
      else {
        this.$swal.fire({
          icon: 'success',
          text: '구독이 완료되었습니다',
          showConfirmButton: false,
          timer: 1500
        })
        this.followToggle()
      }
    },
    followToggle() {
      followToggle(
        this.profile_user,
        () => {
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
    checkIsFollowingUser() {
      followingList(
        this.$store.state.userId,
        (res) => {
          const myFollowings = res.data
          const you = Number(this.profile_user)
          for(let i=0; i<myFollowings.length; i++) {
            const followingUser = myFollowings[i].id
            if (followingUser === you) {
              this.subscribed = true
              return
            }
          }
        },
        (err) => {
          console.error(err);
        }
      );
    },    
    getfollowingList() {
      followerList (
        this.profile_user,
        (res) => {
          this.current_user_followerList = res.data;
          console.log(this.current_user_followerList)
        },
        (err) => {
          console.log(err)
        }
      )
      followingList(
        this.profile_user,
        (res) => {
          this.current_user_followingList = res.data;
        },
        (err) => {
          console.error(err);
        }
      );
    },
    getActiviy() {
      getNotice(
        this.profile_user,
        this.current_user_activity.page_no,
        (res) => {
          this.current_user_activity.page_no = res.data.endNum
            let notices = res.data.noticeResponseDtos;
            if (notices.length < 10) {
              this.current_user_activity.last = true;
            }
            for (let n of notices) {
              this.current_user_activity.list.push(n);
            }
            this.current_user_activity.next = false;
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
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.usercheck();
    this.setFeedList(this.currentFeedName);
    this.getprofileInfo();

  },
  mounted() {
    this.setActivityScroll();
    this.setScroll();
    this.getfollowingList();
    this.getActiviy();
    this.getGroups();
    if(!this.mypage) {
      this.checkIsFollowingUser()
    }
    if (this.$route.query.activetab == 2) {
      this.activetab = 2;
    }
    
  },
};
</script>
