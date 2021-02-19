<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
      <div class="newsfeed-D">
        <GroupNav v-if="Category == 2" :isHome="true" />
        <div v-if="Category == 2&&!isMember" class="newsfeed-detail-not-member">
          <div>
            자세한 내용을 알고 싶다면?
          </div>
          <div>
            그룹에 가입해주세요
          </div>
          <div @click="goToGroupPage" class="newsfeed-detail-not-member-btn">
            그룹으로 이동하기
          </div>
        </div>

        <div v-if="groupModal" class="newsfeed-form-group-container">
          <div class="newsfeed-form-group">
            <span class="newsfeed-share-group-selector">그룹 선택해주세요</span>
            <div class="newsfeed-form-group-list" v-if="groupList.length > 0">
              <div v-for="(group, idx) of groupList" :key="idx">
                <button @click="selectGroup(group)">{{ group.name }}</button>
              </div>
            </div>
            <div class="newsfeed-for  m-group-none" v-else>
              가입된 그룹이 없습니다. 그룹에 가입해주세요!
              <div class="newsfeed-form-group-redirect-btn" @click="goToGroupPage">
                그룹 찾으러 가기
              </div>
            </div>
            <div class="newsfeed-form-group-list-btn">
              <button @click="chooseGroup">닫기</button>
            </div>
          </div>
        </div>
        <section v-if="fd" v-cloak class="feed-detail">
          <div v-if="Category == 2" style="margin-left: 10px; margin-bottom: 10px; cursor: pointer;" @click="goToGroupDetail">
            {{ fd.groupName }}
          </div>
          <div v-if="Category == 2" style="padding: 0 10px; margin-bottom: 5px;">
            <hr style="border-top: 2px solid var(--point-colo);">
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
              </p>
            </div>
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
            :isShare="isShare"
          />

          <div class="feed-detail-like-comment">
            <span @click="clapedList">
              <img width="17px" src="@/assets/icons/clap_icon.svg" style="cursor:pointer;">
              <span>{{ fd.likeCnt ? fd.likeCnt : 0 }}</span>
            </span>
            <span>
              <img width="17px" src="@/assets/icons/comment_icon.svg" alt="">
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

              <img width="17px" v-if="fd.clap" src="@/assets/icons/clap_cancel_icon.svg" alt="">
              <img width="17px" v-else src="@/assets/icons/clap_icon.svg" alt="">
              {{ fd.clap ? "고민해볼래요" : "굉장해요!" }}
            </div>
            <div
              class="feed-detail-like-comment-share-btn"
              @click="focusComment"
            >
              <img width="17px" src="@/assets/icons/comment_icon.svg" alt="">
              댓글달기
            </div>
            <div class="feed-detail-like-comment-share-btn" @click="shareFeed">
              <i class="fas fa-share"></i>
              공유하기
            </div>
          </div>
          <Comment :id="fd.id" :Category="Number(Category)" @createComment="setFeedDetail" />
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

import { readIndoors } from "@/api/indoors.js";
import { createIndoors } from '@/api/indoors.js'

import { getGroupfeedsDetail } from "@/api/group.js";
import { getIsGroupMember } from "@/api/group.js";
import { createGroupFeed } from '@/api/group.js'
import { getprofileGroups } from '@/api/mypage.js'

import { readOutdoors } from "@/api/outdoors.js";
import { createOutdoors } from '@/api/outdoors.js'

import { reedWorker } from "@/api/worker.js"
import { createWorker } from '@/api/worker.js'

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
      shareForm: {},
      groupModal: false,
      groupList: [],
      sel_group_id: null,
      isShare: false,
      isMember: true,
    };
  },
  methods: {
    goToGroupPage () {
      this.$router.push({ name: 'grouppage' })
    },
    chooseGroup () {
      this.groupModal = false;
    },
    closeClapList() {
      this.clapListOpen = false;
    },
    clapedList() {
      this.clapListOpen = true;
      clapFeedList(
        this.fd.id,
        (res) => {
          this.clapedUsers = res.data.user;
        },
        (err) => {
          console.log(err);
        }
      );
    },
    likeFeed() {
      clapFeed(
        this.fd.id,
        () => {
          if (!this.fd.clap) {
            this.$swal.fire({
              icon: 'success',
              text: '좋아요',
              showConfirmButton: false,
              timer: 1500
            })
            this.fd.likeCnt = this.fd.likeCnt + 1;
            this.fd.clap = true;
          } else {
            // alert("좋아요 취소!");
            this.$swal.fire({
              icon: 'success',
              text: '좋아요 취소',
              showConfirmButton: false,
              timer: 1500
            })
            this.fd.likeCnt = this.fd.likeCnt - 1;
            this.fd.clap = false;
          }
        },
        (err) => {
          console.log(err);
        }
      );
    },
    selectGroup (g) {
      this.sel_group_id = g.id
      this.groupModal = false;
      createGroupFeed(
        g.id,
        this.shareForm,
        (res) => {
          this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category, group: g.id } })
        },
        (err) => {
          console.log(err)
        }
      )
    },
    shareFeed() {
      // const answer = window.confirm("내 피드에 공유하시겠습니까?");

      this.$swal.fire({
        text: '내 피드에 공유하시겠습니까?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '공유하기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          if (this.fd.files) {
            let urls = ""
            for (let i of this.fd.files) {
              let fileUrl = `https://dtbqjjy7vxgz8.cloudfront.net/${i}`
              let img = `<div style="width:100%; background-color: rgb(0, 0, 0, 0.8);> <img width="100%" src="${fileUrl}" alt="미리보기 이미지" /> </div>`
              urls = urls + '<br>' + img
            }
            this.shareForm.content = `<b>[공유]</b> <a href="${document.location.href}">원문이동</a> <br> ${urls} ${this.fd.content}`
          } else {
            this.shareForm.content = `<b>[공유]</b> <a href="${document.location.href}">원문이동</a> ${this.fd.content}`
          }
            this.shareForm.filePaths = this.fd.files
            this.shareForm.tags = this.fd.tags
          if (this.Category == 3) {
            this.shareForm.lat = this.fd.lat
            this.shareForm.lng = this.fd.lng
            this.shareForm.placeName = this.fd.placeName
            this.shareForm.address = this.fd.address
          }
          if (this.Category == 1) {
            createIndoors(
              this.shareForm,
              (res) => {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              },
              (err) => {
                console.log(err)
              }
            )
          } else if (this.Category == 2) {
            getprofileGroups(
              this.userpk,
              (res) => {
                this.groupList = res.data
                this.groupModal = true;
              },
              (err) => {
                console.log(err)
              }  
            )
          } else if (this.Category == 3) {
            createOutdoors(
              this.shareForm,
              (res) => {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              },
              (err) => {
                console.log(err)
              }
            )
          } else if (this.Category == 4) {
            createWorker(
              this.shareForm,
              (res) => {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              },
              (err) => {
                console.log(err)
              }
            )
          } else {
            // alert('잘못된 접근입니다.')
            this.$swal.fire({
              icon: 'error',
              text: '잘못된 접근입니다',
              showConfirmButton: false,
              timer: 1500
            })
          }
          // this.$router.push({
          //   name: "NewsfeedForm",
          //   query: { Category: this.Category },
          //   params: {
          //     type: "SHARE",
          //     feed: this.fd,
          //     link: document.location.href,
          //   },
          // });
        
        } 
      })
      
    },
    focusComment() {
      let input = document.getElementById("comment");
      input.focus();
    },
    goToGroupDetail () {
      this.$router.push({
        name: "groupdetail",
        query: { groupId: this.fd.groupId},
      });
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
      if (this.Category == 1) {
        readIndoors(
          this.$route.query.id,
          (res) => {
            this.fd = res.data;
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>"); // 엔터 반영하는 코드..? 맞나 form 정상되면 테스트
            let st = this.fd.content.split(" ")[0]
            if (st == '<b>[공유]</b>') {
              this.isShare = true;
            }
          },
          (err) => {
            console.log(err);
          }
        );
      } else if (this.Category == 2) {
        // 핵인싸 get 요청
        if(!this.$route.query.group) {
          this.$router.push({name : "NotFound"});
        }
        getGroupfeedsDetail(
          this.$route.query.group,
          this.$route.query.id,
          (res) => {
            this.fd = res.data;
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>"); // 엔터 반영하는 코드..? 맞나 form 정상되면 테스트
            let st = this.fd.content.split(" ")[0]
            if (st == '<b>[공유]</b>') {
              this.isShare = true;
            }
            getIsGroupMember(
              this.fd.id,
              (res) => {
                this.isMember = res.data
              },
              (err) => {
                console.log(err)
              }
            )
          },
          (err) => {
            console.log(err);
          }
        );
        // latitude / longitude / placeName 설정해주기~
      } else if (this.Category == 3) {
        // 청산별곡 get 요청
        readOutdoors(
          this.$route.query.id, 
          (res) => {
            this.fd = res.data;
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>");
            this.placeName = this.fd.placeName;
            this.address = this.fd.address;
            this.longitude = this.fd.lng;
            this.latitude = this.fd.lat;
            let st = this.fd.content.split(" ")[0]
            if (st == '<b>[공유]</b>') {
              this.isShare = true;
            }
          },
          (err) => {
            console.log(err);
          }
        );
        // latitude / longitude / placeName 설정해주기~
      } else if (this.Category == 4) {
        // 워커홀릭 get 요청
        reedWorker(
          this.$route.query.id,
          (res) => {
            this.fd = res.data;
            this.date = this.fd.date.split("T")[0];
            this.time = this.fd.date.split("T")[1];
            this.fd.content = this.fd.content.replace(/(\n|\r\n)/g, "<br>"); // 엔터 반영하는 코드..? 맞나 form 정상되면 테스트
            let st = this.fd.content.split(" ")[0]
            if (st == '<b>[공유]</b>') {
              this.isShare = true;
            }
          },
          (err) => {
            console.log(err);
          }
        );
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
    this.setFeedDetail();
    this.checkCategory()
    .then((res)=>{ 
      if (!res) {
        this.$router.push({name : "NotFound"});
      }
    })
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
