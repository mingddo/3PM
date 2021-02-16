<template>
  <div>
    <div class="newsfeed-body" >
        <Sidebar />
      <div class="group-detail">
        <GroupNav :isHome="false"/>
        <div class="group-detail-header">
          <div class="group-detail-img-space" v-if="!modiForm">
            <img class="group-detail-img" :src="group_info.groupImg ? `https://dtbqjjy7vxgz8.cloudfront.net/${group_info.groupImg}` : 'https://picsum.photos/200'" alt="그룹이미지" />
          </div>
          <div class="group-detail-img-form" v-else>
            <label class="group-detail-img-space" for="groupImage"> <img class="group-detail-img group-detail-img-form" :src="imageUrl ? imageUrl : `https://dtbqjjy7vxgz8.cloudfront.net/${group_info.groupImg}`" alt="그룹이미지" /> </label>
            <input
              id="groupImage"
              type="file"
              accept="image/*"
              @change="selectGroupImg"
            />
          </div>
          <div v-if="group_info.leaderId == userpk" class="group-detail-modi">
            <i v-if="modiForm" class="far fa-check-square group-detail-modi-btn"  @click="changeModiForm"></i>
            <i v-else class="fas fa-edit group-detail-modi-btn"  @click="changeModiForm"></i>
            <!-- {{ modiForm ? '수정' : '그룹 수정하기'}} -->
          </div>
          <div class="group-detail-title" v-if="!modiForm">{{ group.name }}</div>
          <div class="group-detail-title-input-box" v-else>
            <input class="group-detail-title-input" type="text" v-model="group.name">
          </div>
          <div class="group-detail-introduce" v-if="!modiForm">
            {{ group.description }}
          </div>
          <div class="group-detail-introduce-input-box" v-else>
            <input class="group-detail-introduce-input" type="text" v-model="group.description">
          </div>
          <div v-if="!isjoined" class="group-detail-join-btn"  @click="join">
              그룹 가입하기
          </div>
          <div class="group-detail-info">
            <div>
              <div>그룹장</div>
              <div class="group-detail-leader" @click="goToleaderProfile">
                {{ group_info.leaders[0].nickname }}
              </div>
            </div>
            <div>
              <div>회원수</div>
              <div class="group-detail-member" @click="memberListOpen">{{ group_info.memberCnt }}</div>
            </div>
          </div>
          <UserList
            type=2
            v-if="memberModal"
            :users="group_info.members"
            @closeList="closeMemberList"
          />
          <hr>
          <div v-if="group_info.leaders[0].id =! userpk" class="group-detail-secede-btn-pos">
            <span class="group-detail-secede-btn" @click="secede">그룹 탈퇴하기</span> 
          </div>
          <div v-else class="group-detail-secede-btn-pos">
            <span class="group-detail-secede-btn" @click="groupDelete">그룹 삭제하기</span>  
          </div>
        </div>

        <section>
          <GroupRecommend />
        </section>
        <section>
          <NewsFeedList
            :selectGroup="group_info"
            :Category="2"
            :feed="feed"
            :last="last"
            :next="next"
          />
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import { joinGroup } from '@/api/group.js'
import { secedeGroup } from '@/api/group.js'
import { getGroupDetail } from '@/api/group.js'
import { updateGroupInfo } from '@/api/group.js'
import { getGroupfeeds } from '@/api/group.js'
import { deleteGroup } from '@/api/group.js'
import { createGroupImg } from '@/api/group.js'

import { mapState } from 'vuex'

import Sidebar from '../../components/Common/Sidebar.vue';
import GroupRecommend from '../../components/GroupFeed/GroupRecommend.vue';
import NewsFeedList from '../../components/NewsFeed/NewsFeedList.vue';
import GroupNav from '../../components/GroupFeed/GroupNav.vue';
import UserList from '../../components/NewsFeed/Common/UserList.vue'

export default {
  components: { Sidebar, GroupRecommend, NewsFeedList, GroupNav, UserList },
  data() {
    return {
      modiForm: false,
      feed: [],
      page: 0,
      last: false,
      next: false,
      didScroll: false,
      scrolled: false,
      lastScrollTop: 0,
      delta: 5,
      navbarHeight: 70,
      isjoined: "false",
      group: {},
      selectedFile: null,
      imageUrl: null,
      group_info: {},
      memberModal: false,
    };
  },
  methods: {
    selectGroupImg (e) {
      let files = e.target.files
      if (files.length) {
        this.selectedFile = files[0]
        let reader = new FileReader();
        reader.onload = (e) => {
          this.imageUrl = e.target.result;
          console.log(this.imageUrl)
          // this.previewUrl.push(this.imageUrl)
        }
        reader.readAsDataURL(this.selectedFile);
      }
    },
    goToleaderProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.group_info.leaderId }})
    },
    closeMemberList () {
      this.memberModal = false;
    },
    memberListOpen (){
      this.memberModal = true;
    },
    groupDelete () {
      const answer = window.confirm('한번 삭제된 그룹 데이터는 복원되지 않습니다. 삭제하시겠습니까?')
      if (answer) {
        deleteGroup(
          this.group_info.id,
          (res) => {
            console.log(res)
            this.$router.push({ name: 'grouppage' })
          },
          (err) => {
            console.log(err)
          }
        )
      }
    },
    changeModiForm () {
      if (this.modiForm == true) {
        updateGroupInfo(
          this.group_info.id,
          this.group,
          (res) => {
            console.log(res)
          },
          (err) => {
            console.log(err)
          }
        )
        if (this.selectedFile) {
          const formData = new FormData ();
          formData.append('file', this.selectedFile)
          createGroupImg(
            this.group_info.id,
            formData,
            (res) => {
              console.log(res)
            },
            (err) => {
              console.log(err)
            }
          )
        }
      }
      this.modiForm = !this.modiForm;
    },
    secede () {
      const answer = window.confirm('정말로 탈퇴하시겠습니까?')
      if (answer) {
        secedeGroup(
          this.group_info.id,
          (res) => {
            console.log(res)
            alert('그룹에 탈퇴되었습니다!')
            this.isjoined = false
          },
          (err) => {
            console.log(err)
          }
        )
      }
    },
    join () {
      joinGroup(
        this.group_info.id,
        (res) => {
          console.log(res)
          alert('그룹에 가입되었습니다!')
        },
        (err) => {
          console.log(err)
        }
      )
    },
    setFeedList() {
      getGroupfeeds(
        this.$route.query.groupId,
        this.page,
        (res) => {
          console.log(res)
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
    },
    getgroupInfo() {
      // axios작성 부분
      getGroupDetail(
        this.$route.query.groupId,
        (res) => {
          console.log('그룹정보', res.data)
          this.group_info = res.data
          this.group.name = this.group_info.name
          this.group.description = this.group_info.description

          this.validjoin();
        },
        (err) => {
          console.log(err)
        }
      )
    },
    validjoin() {
      this.group_info.group_people_id.map((user) => {
        if (user === this.$store.state.userId) {
          return (this.isjoined = true);
        } else {
          return (this.isjoined = false);
        }
      });
      console.log(this.isjoined);
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
    this.getgroupInfo();
    this.setFeedList();
    // this.validjoin();
  },
  mounted () {
    this.setScroll();
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style>
.group-detail {
  width: 70%;
  margin-top: 80px;
  border-radius: 50px;
}
.group-detail-header {
  width: 90%;
  margin: auto;
  padding: 40px;
}
.group-detail-img-space {
  margin: auto;
  /* cursor: pointer; */
  width: 150px;
  height: 150px;
  text-align: left;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 100%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}
.group-detail-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.group-detail-img-form{
  cursor: pointer
}
.group-detail-img-form input[type="file"] { 
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
.group-detail-title {
  margin: 20px;
  font-size: 24px;
  text-align: center;
}
.group-detail-title-input-box {
  display: flex;
  justify-content: center;
}
.group-detail-title-input {
  margin: 20px;
  font-size: 24px;
}
.group-detail-introduce {
  text-align: center;
  font-size: 14px;
}
.group-detail-introduce-input-box {
    display: flex;
  justify-content: center;
}
.group-detail-introduce-input {
  font-size: 14px;
}
.group-detail-modi-btn {
  cursor: pointer;
}
.group-detail-join-btn {
  width: 100%;
  height: 50px;
  justify-content: center;
  display: flex;
  align-items: center;
  border-radius: 10px;
  background-color: #e4e6e9;
  box-shadow: 0 1px 3px rgb(0 0 0 / 20%);
  cursor: pointer;
  margin: 15px;
}
.group-detail-leader {
  cursor: pointer;
}
.group-detail-member {
  cursor: pointer;
}
.group-detail-secede-btn-pos {
  text-align: right;
}
.group-detail-secede-btn {
  cursor: pointer;
  font-size: 12px;
}
.group-detail-info {
  margin: auto;
  display: flex;
  justify-content: space-evenly;
}
.group-detail-info > div > div {
  margin: 10px;
  text-align: center;
}
.group-detail-info > div > div:last-child {
  font-size: 14px;
}
.group-detail-modi {
  text-align: right;
}
@media screen and (max-width: 1050px) {
  .group-detail {
    margin-top: 100px;
    width: 100%;
  }
  .group-detail-join-btn {
    margin-left: 0;
    margin-right: 0;
  }
}
</style>
