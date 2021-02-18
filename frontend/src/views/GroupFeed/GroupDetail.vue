<template>
  <div>
    <div class="newsfeed-body" >
        <Sidebar />
      <div class="group-detail" v-if="group_info">
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
            <div class="group-modi-img-guide">
              사진을 클릭하여 사진을 변경해주세요.
            </div>
          </div>
          <div>
            
          </div>
          <div class="group-detail-body">
            <div class="group-detail-title" v-if="!modiForm">{{ group.name }}</div>
            <div class="group-detail-info">
                <div v-if="leaderInfo" class="group-detail-leader" @click="goToleaderProfile">그룹장 : {{ leaderInfo.nickname }}</div>
                <div class="group-detail-member" @click="memberListOpen">회원수 : {{ group_info.memberCnt }}</div>
            </div>
          </div>
          <div class="group-detail-title-input-box" v-if="modiForm">
            <input id="groupName" class="group-detail-title-input" type="text" v-model="group.name">
          </div>
          <div class="group-detail-introduce" v-if="!modiForm">
            {{ group.description }}
          </div>
          <div class="group-detail-introduce-input-box" v-else>
            <textarea id="group-intro" class="group-detail-introduce-input" cols="30" rows="10"  v-model="group.description"></textarea>
          </div>
          <div v-if="modiForm" class="group-detail-modi-save" @click="changeModiForm">
            <i  class="far fa-check-square"> </i>저장
          </div>
          <div v-if="!isjoined && !( userId == leader)" class="group-detail-join-btn"  @click="join">
              그룹 가입하기
          </div>
          <UserList
            type=2
            v-if="memberModal"
            :users="group_info.members"
            @closeList="closeMemberList"
          />
          <hr style="border-top: 2px var(--light-brown)">
          <div class="group-detail-edit">
            <div v-if="group_info.leaderId == userId" class="group-detail-modi">
              <div v-if="!modiForm" class="group-detail-modi-btn" @click="changeModiForm">
                <i  class="fas fa-edit group-detail-modi-btn"></i>수정하기
              </div>
            </div>
            <div v-if="isjoined" class="group-detail-secede-btn-pos">
              <span class="group-detail-secede-btn" @click="secede">그룹 탈퇴하기</span> 
            </div>
            <div v-if="leader == userId" class="group-detail-secede-btn-pos">
              <span class="group-detail-secede-btn" @click="groupDelete">그룹 삭제하기</span>  
            </div>
          </div>
        </div>

        <section>
          <!-- <GroupRecommend /> -->
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
// import GroupRecommend from '../../components/GroupFeed/GroupRecommend.vue';
import NewsFeedList from '../../components/NewsFeed/NewsFeedList.vue';
import GroupNav from '../../components/GroupFeed/GroupNav.vue';
import UserList from '../../components/NewsFeed/Common/UserList.vue'

export default {
  components: { Sidebar, NewsFeedList, GroupNav, UserList },
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
      leader: null,
      leaderInfo: null,
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
        }
        reader.readAsDataURL(this.selectedFile);
      }
    },
    goToleaderProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.leader }})
    },
    closeMemberList () {
      this.memberModal = false;
    },
    memberListOpen (){
      this.memberModal = true;
    },
    groupDelete () {
      this.$swal.fire({ 
        text: '한번 삭제된 그룹 데이터는 복원되지 않습니다. 삭제하시겠습니까?', 
        icon: 'warning', 
        showCancelButton: true, 
        confirmButtonText: '삭제하기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          deleteGroup(
            this.group_info.id,
            () => {
              this.$router.push({ name: 'grouppage' })
            },
            (err) => {
              console.log(err)
            }
          )
        }
      });

      // const answer = window.confirm('한번 삭제된 그룹 데이터는 복원되지 않습니다. 삭제하시겠습니까?')
      // if (answer) {
      //   deleteGroup(
      //     this.group_info.id,
      //     () => {
      //       this.$router.push({ name: 'grouppage' })
      //     },
      //     (err) => {
      //       console.log(err)
      //     }
      //   )
      // }
    },
    changeModiForm () {
      if (this.modiForm == true) {
        updateGroupInfo(
          this.group_info.id,
          this.group,
          () => {
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
            () => {
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
      this.$swal.fire({ 
        text: '정말로 탈퇴하시겠습니까?', 
        icon: 'warning', 
        showCancelButton: true, 
        confirmButtonText: '탈퇴하기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          secedeGroup(
            this.group_info.id,
            () => {            
              this.$swal.fire({
                icon: 'success',
                text: '그룹에 탈퇴되었습니다',
                showConfirmButton: false,
                timer: 1500
              })
              this.isjoined = false 
              this.getgroupInfo();

            },
            (err) => {
              console.log(err)
            }
          )
        }
      });

      // const answer = window.confirm('정말로 탈퇴하시겠습니까?')
      // if (answer) {
      //   secedeGroup(
      //     this.group_info.id,
      //     () => {
      //       alert('그룹에 탈퇴되었습니다!')
      //       this.isjoined = false 
      //     },
      //     (err) => {
      //       console.log(err)
      //     }
      //   )
      // }
    },
    join () {
      joinGroup(
        this.group_info.id,
        () => {
          // alert('그룹에 가입되었습니다!')
          this.$swal.fire({
            icon: 'success',
            text: '그룹에 가입되었습니다',
            showConfirmButton: false,
            timer: 1500
          })
          this.isjoined = true 
          this.getgroupInfo();

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
          this.group_info = res.data
          this.group.name = this.group_info.name
          this.group.description = this.group_info.description
          if (this.group_info.leaderId) {
            this.leader = this.group_info.leaderId
          } else if (this.group_info.leader.id) {
            this.leader = this.group_info.leader.id
          }
          this.leaderInfo = this.group_info.leaders[0]
          this.validjoin();
        },
        (err) => {
          console.log(err)
        }
      )
    },
    validjoin() {
      const me = this.$store.state.userId
      for (let i=0; i<this.group_info.members.length;i++) {
        const member = this.group_info.members[i]
        const memberId = member.id
        if (memberId === me) {
          this.isjoined = true
          return
        }
      }
      this.isjoined = false
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
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.getgroupInfo();
    this.setFeedList();
  },
  mounted () {
    this.setScroll();
  },
  computed: {
    ...mapState(["userId"]),
  },
};
</script>

<style>
.group-detail {
  width: 50%;
  margin-top: 80px;
  border-radius: 50px;
}
.group-detail-header {
  background-color: #fffcf9;
  border-radius: 20px;
  width: 100%;
  margin: auto;
  margin-bottom: 30px;
  
}
.group-detail-img-space {
  margin: auto;
  width: auto;
  height: 60%;
  border-radius: 20px;
  text-align: left;
  border: solid 1px rgba(0, 0, 0, 0.2);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(0, 0, 0, 0.8);
}
.group-detail-body {
  display: flex;
  justify-content: space-between;
}
.group-detail-img {
  /* width: 100%; */
  height: 100%;
  max-height: 300px;
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
  padding: 20px;
  font-size: 24px;
  display: flex;
  align-items: center;
}
.group-detail-title-input-box div {
  text-align: center;
}
.group-detail-title-guide {
  font-size: 12px;
}
.group-detail-title-input-box {
  font-size: 12px;
  text-align: center;
}
.group-detail-title-input {
  width: 90%;
  padding: 10px;
  border: 0;
  background-color: transparent;
  border-bottom: 2px solid #9e7f6d;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-detail-introduce {
  padding-left: 20px;
  padding-right: 20px;
  font-size: 14px;
  margin-bottom: 20px;
}
.group-detail-introduce-input-box {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.group-detail-introduce-input {
  width: 100%;
  padding: 10px;
  border: none;
  background-color: #fffcf9;
  border-radius: 10px;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-detail-modi {
  display: flex;
  /* text-align: right; */
}
.group-detail-modi-btn {
  cursor: pointer;
  font-size: 16px;
}
.group-detail-modi-save {
  margin-top: 5px;
  cursor: pointer;
  background-color: #fff9f3;
  border-radius: 5px;
  padding : 5px;
  text-align: center;
}
.group-detail-join-btn {
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
  font-size: 14px;
}
.group-detail-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 20px;
}
.group-detail-info div {
  font-size: 14px;
}
.group-modi-img-guide {
  margin-top: 5px;
  text-align: center;
}
.group-modi-img-guide > div {
  margin-top: 10px;
  font-size: 12px;
}
.group-detail-edit {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 20px;
  padding-right: 20px;
}
@media screen and (max-width: 768px) {
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
