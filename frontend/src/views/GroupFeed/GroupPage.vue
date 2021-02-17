<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
      <div class="group-detail">
        <GroupNav :isHome="false"/>
        <GroupReco :recoGroup="recoGroup"/>
        <!-- <GroupRecommend :recoGroup="recoGroup"/> -->
        <div class="group-create-btn" @click="goto_create_group">그룹 만들기</div>
        <!-- <GroupCard v-for="(group, idx) in groupList" :key="idx" :group="group"/> -->
        <section class="group-page-content">
          <div class="group-page-content-header">내 그룹</div>
          <GroupJoined v-if="groupList.length != 0" :groupList="groupList"/>
          <GroupNotFound v-else/>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getprofileGroups } from '@/api/mypage.js'
import { getAllGroup } from '@/api/group.js'
// import GroupRecommend from "@/components/GroupFeed/GroupRecommend.vue";
import GroupJoined from '../../components/GroupFeed/GroupJoined.vue';
import GroupNotFound from '../../components/GroupFeed/GroupNotFound.vue';
import Sidebar from '../../components/Common/Sidebar.vue';
import GroupNav from '../../components/GroupFeed/GroupNav.vue';
import GroupReco from '../../components/GroupFeed/GroupReco.vue';
export default {
  components: { GroupJoined, GroupNotFound, Sidebar, GroupNav, GroupReco },
  data() {
    return {
      // groups: [{}, {}, {}, {}],
      groupList: [],
      recoGroup: [],
    };
  },
  created () {
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.getGroupList();
  },
  methods: {
    getGroupList () {
      getAllGroup(
        (res) => {
          if (res.data.length < 9) {
            this.recoGroup = res.data
          } else {
            this.recoGroup = res.data.slice(0, 9)
          }
          console.log('그룹', this.recoGroup)
        },
        (err) => {
          console.log(err)
        }
      ),

      getprofileGroups(
        this.userpk,
        (res) => {
          // console.log(res)
          this.groupList = res.data
        },
        (err) => {
          console.log(err)
        }
      )
    },
    goto_create_group() {
      this.$router.push({ name: "groupform" });
    },
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style>
.group-create-btn {
  position: fixed;
  bottom: 25px;
  right: 10px;
  border-radius: 10px;
  border: 1px solid black;
  background-color: rgba(255,255,255,0.5);
  padding: 10px;
}
.group-page-content {
  min-height: 300px;
}
.group-page-content-header {
  font-size : 24px;
  margin: 20px;
}
</style>
