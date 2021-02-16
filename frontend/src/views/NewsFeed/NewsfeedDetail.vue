<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
      <div class="newsfeed-D">
        <GroupNav v-if="Category == 2" :isHome="true" />
        <section v-if="fd" v-cloak class="feed-detail">
          <div v-if="Category == 2">
            {{ fd.groupName }}
          </div>
          <div class="feed-detail-userprofile">
            <div class="feed-detail-userprofile-box">
              <NewsFeedProfile
                :proImg="fd.user.img !== null ? fd.user.img : defaultImg"
                :userId="fd.user.id"
              />
              <div class="feed-detail-userprofile-content">
                <div>
                  <h3 class="feed-detail-userprofile-name" @click="goToProfile">
                    {{
                      fd.user.nickname !== null
                        ? fd.user.nickname
                        : "회원정보 없음"
                    }}
                  </h3>
                </div>
                <div>
                  <p>{{ date }} {{ time }}</p>
                </div>
              </div>
            </div>

            <UserInfoBtn
              class="feed-detail-userprofile-dropBtn"
              :id="fd.user.id"
              :follow="fd.follow"
              :name="fd.user.nickname"
            />
          </div>

          <article class="feed-detail-content-box">
            <div
              class="feed-detail-content-box-innerbox"
              v-if="fd.files.length != 0"
            >
              <div
                class="feed-detail-img-containerFrame"
                v-for="(file, idx) in fd.files"
                :key="idx"
              >
                <img
                  :src="`https://dtbqjjy7vxgz8.cloudfront.net/${file}`"
                  class="feed-detail-img"
                  alt="업로드 파일"
                />
              </div>
            </div>
            <div class="feed-detail-tag">
              <button
                v-for="(tag, idx) in fd.tags"
                :key="idx"
                @click="goToSearchTag(tag)"
              >
                {{ tag }}
              </button>
            </div>
            <div v-if="fd.content" class="feed-detail-content">
              <p v-html="fd.content">
                <!-- {{fd.content}} -->
              </p>
            </div>
            <!-- <div v-if="Category == 2 || Category == 3"> -->
            <Location
              v-if="Category == 3 && latitude && longitude"
              :address="address"
              :latitude="latitude"
              :longitude="longitude"
              :placeName="placeName"
            />
          </article>

          <ModiAndDelete
            class="feed-detail-modi-delete"
            v-if="userpk == fd.user.id"
            :fd="fd"
            :Category="Number(Category)"
          />

          <div class="feed-detail-like-comment">
            <span @click="clapedList">
              <span v-if="fd.clap">👏🏻</span>
              <img
                v-if="!fd.clap"
                src="https://img.icons8.com/fluent-systems-regular/17/000000/applause.png"
              />
              <span>{{ fd.likeCnt ? fd.likeCnt : 0 }}</span>
            </span>
            <span>
              <i class="far fa-comment"></i>
              <span v-if="fd.commentCnt">{{ fd.commentCnt }}</span>
              <span v-else> 0</span>
            </span>
          </div>
          <UserList
            :type="1"
            v-if="clapListOpen"
            :users="clapedUsers"
            @closeList="closeClapList"
          />
          <div class="feed-detail-like-comment-share-btn-box">
            <div @click="likeFeed" class="feed-detail-like-comment-share-btn">
              <img
                v-if="!fd.clap"
                src="https://img.icons8.com/fluent-systems-regular/17/000000/applause.png"
              />
              <span v-if="fd.clap">👏🏻</span>
              {{ fd.clap ? "고민해볼래요" : "굉장해요!" }}
            </div>
            <div
              class="feed-detail-like-comment-share-btn"
              @click="focusComment"
            >
              <i class="far fa-comment"></i>
              댓글달기
            </div>
            <div class="feed-detail-like-comment-share-btn" @click="shareFeed">
              <i class="fas fa-share"></i>
              공유하기
            </div>
          </div>
          <Comment :id="fd.id" :Category="Number(Category)" />
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { readIndoors } from "@/api/indoors.js";
import { getGroupfeedsDetail } from "@/api/group.js";
import { readOutdoors } from "@/api/outdoors.js";
import { reedWorker } from "@/api/worker.js";
import { clapFeed } from "@/api/feed.js";
import { clapFeedList } from "@/api/feed.js";
import Sidebar from "../../components/Common/Sidebar.vue";
import NewsFeedProfile from "../../components/NewsFeed/Common/NewsFeedProfile.vue";
import UserList from "../../components/NewsFeed/Common/UserList.vue";
import ModiAndDelete from "../../components/NewsFeed/Detail/ModiAndDelete.vue";
import UserInfoBtn from "../../components/NewsFeed/Detail/UserInfoBtn.vue";
import Location from "../../components/NewsFeed/Detail/Location.vue";
import Comment from "../../components/NewsFeed/Detail/Comment.vue";
import GroupNav from "../../components/GroupFeed/GroupNav.vue";

export default {
  name: "NewsfeedDetail",
  components: {
    Sidebar,
    NewsFeedProfile,
    UserList,
    ModiAndDelete,
    UserInfoBtn,
    Location,
    Comment,
    GroupNav,
  },
  data() {
    return {
      Category: null,
      fd: null,
      commentInput: "",
      date: null,
      time: null,
      mention: [],
      auto: false,
      items: [],
      comments: [],
      clapedUsers: [],
      clapListOpen: false,
      latitude: null,
      longitude: null,
      placeName: null,
      address: null,
    };
  },
  methods: {
    closeClapList() {
      this.clapListOpen = false;
    },
    clapedList() {
      this.clapListOpen = true;
      clapFeedList(
        this.fd.id,
        (res) => {
          this.clapedUsers = res.data.user;
          console.log(this.clapedUsers);
        },
        (err) => {
          console.log(err);
        }
      );
    },
    likeFeed() {
      clapFeed(
        this.fd.id,
        (res) => {
          if (!this.fd.clap) {
            alert(`좋아요!`);
            this.fd.likeCnt = this.fd.likeCnt + 1;
            this.fd.clap = true;
          } else {
            alert("좋아요 취소!");
            this.fd.likeCnt = this.fd.likeCnt - 1;
            this.fd.clap = false;
          }

          console.log(res);
        },
        (err) => {
          console.log(err);
        }
      );
    },
    shareFeed() {
      const answer = window.confirm("내 피드에 공유하시겠습니까?");
      if (answer) {
        this.$router.push({
          name: "NewsfeedForm",
          query: { Category: this.Category },
          params: {
            type: "SHARE",
            feed: this.fd,
            link: document.location.href,
          },
        });
      }
    },
    focusComment() {
      let input = document.getElementById("comment");
      input.focus();
    },
    goToProfile() {
      this.$router.push({ name: "MyPage", query: { name: this.fd.user.id } });
    },
    goToSearchTag(tag) {
      this.$router.push({
        name: "Search",
        query: { query: tag },
        params: { filter: "feed" },
      });
    },
    setFeedDetail() {
      // feed.pk 를 활용하여 detail 페이지 요청 보내기
      // 현재는 가상 데이터 하나만 고정해서 보여주기
      this.Category = this.$route.query.Category;
      console.log("카테고리", this.Category);
      if (this.Category == 1) {
        readIndoors(
          this.$route.query.id,
          (res) => {
            this.fd = res.data;
            console.log(res.data);
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>"); // 엔터 반영하는 코드..? 맞나 form 정상되면 테스트
          },
          (err) => {
            console.log(err);
          }
        );
      } else if (this.Category == 2) {
        // 핵인싸 get 요청
        getGroupfeedsDetail(
          this.$route.query.group,
          this.$route.query.id,
          (res) => {
            this.fd = res.data;
            console.log(res.data);
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>"); // 엔터 반영하는 코드..? 맞나 form 정상되면 테스트
          },
          (err) => {
            console.log(err);
          }
        );
        // latitude / longitude / placeName 설정해주기~
      } else if (this.Category == 3) {
        // 청산별곡 get 요청
        readOutdoors(this.$route.query.id, (res) => {
          this.fd = res.data;
          console.log(this.fd);
          this.date = this.fd.date.split("T")[0];
          this.time = this.fd.date.split("T")[1];
          this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>");
          this.placeName = this.fd.placeName;
          this.address = this.fd.address;
          this.longitude = this.fd.lng;
          this.latitude = this.fd.lat;
        });
        // latitude / longitude / placeName 설정해주기~
      } else if (this.Category == 4) {
        // 워커홀릭 get 요청
        reedWorker(
          this.$route.query.id,
          (res) => {
            this.fd = res.data;
            console.log(res.data);
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>"); // 엔터 반영하는 코드..? 맞나 form 정상되면 테스트
          },
          (err) => {
            console.log(err);
          }
        );
      }
    },
  },
  created() {
    this.setFeedDetail();
  },
  computed: {
    ...mapState({
      userpk: (state) => state.userId,
      defaultImg: (state) => state.defaultImg,
    }),
  },
};
</script>

<style>
.feed-comment-input {
  width: 100%;
  height: 50px;
  border: none;
}
.feed-detail-map {
  margin: auto;
}
.feed-detail-map-name {
  text-align: right;
}
.feed-detail-map-placeName {
  font-weight: bold;
}
.feed-detail-comment-plus {
  text-align: center;
  padding: 10px;
  cursor: pointer;
  border-radius: 30px;
}
.feed-detail-comment-plus:hover {
  box-shadow: 0px 5px 10px rgb(0 0 0 / 20%);
}
.feed-detail-comment-empty {
  text-align: center;
}
</style>
