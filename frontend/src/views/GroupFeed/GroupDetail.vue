<template>
  <div>
    <div class="newsfeed-body" >
        <Sidebar />
      <div class="group-detail">
        <GroupNav :isHome="false"/>
        <div class="group-detail-header">
          <div class="group-detail-img-space">
            <img class="group-detail-img" src="https://picsum.photos/200" alt="">
          </div>
          <div class="group-detail-title">{{ group_info.name }}</div>
          <div class="group-detail-introduce">
            {{ group_info.description }}
          </div>
          <div class="group-detail-join-btn">
            <span v-if="!isjoined" @click="join">
              그룹 가입하기
            </span>
            <span v-else @click="secede">
              그룹 탈퇴하기
            </span>
          </div>
          <div class="group-detail-info">
            <div>
              <div>그룹장</div>
              <div v-for="(leader, idx) in group_info.leaders" :key="idx">
                {{leader.nickname}}
              </div>
            </div>
            <div>
              <div>회원수</div>
              <div>{{ group_info.memberCnt }}</div>
            </div>
          </div>
          <hr>
        </div>

        <section>
          <GroupRecommend />
        </section>
        <section>
          <NewsFeedList />
        </section>
      </div>
    </div>
  </div>
</template>

<script>
// import NewsFeedHeader from "@/components/NewsFeed/NewsFeedHeader.vue";
// import NewsFeedList from "@/components/NewsFeed/NewsFeedList.vue";
import { joinGroup } from '@/api/group.js'
import { secedeGroup } from '@/api/group.js'
import { getGroupDetail } from '@/api/group.js'
import Sidebar from '../../components/Common/Sidebar.vue';
import GroupRecommend from '../../components/GroupFeed/GroupRecommend.vue';
import NewsFeedList from '../../components/NewsFeed/NewsFeedList.vue';
import GroupNav from '../../components/GroupFeed/GroupNav.vue';

export default {
  components: { Sidebar, GroupRecommend, NewsFeedList, GroupNav },
  data() {
    return {
      isjoined: "false",
      group_info: {
        // id: 1,
        // group_img: "",
        name: "볼링 너 밖에 없어",
        description: "안녕하세요 여기는 볼링을 사랑하는 볼링 그룹입니다.",
        leaders: [
          {
            id: 1,
            nickname: "박성호"
          }
        ],
        memberCnt: "502",
        // group_people_id: [1, 2, 3, 4, 5, 9],
      },
    };
  },
  methods: {
    secede () {
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
    getgroupInfo() {
      // axios작성 부분
      getGroupDetail(
        this.$route.query.groupId,
        (res) => {
          console.log(res)
          this.group_info = res.data
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
  },
  created() {
    // this.getgroupInfo();
    // this.validjoin();
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
  cursor: pointer;
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
.group-detail-title {
  margin: 20px;
  font-size: 24px;
  text-align: center;
}
.group-detail-introduce {
  text-align: center;
  font-size: 14px;
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
