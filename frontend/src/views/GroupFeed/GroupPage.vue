<template>
  <div>
    <Sidebar />
    <div class="newsfeed-header" >
      <NewsFeedHeader :Category="3"/>
    </div>
    <div class="group-detail">
      <GroupRecommend />
      <div class="group-create-btn" @click="goto_create_group">그룹 만들기</div>
      <!-- <GroupCard v-for="(group, idx) in groupList" :key="idx" :group="group"/> -->
      <hr>
      <section class="group-page-content">
        <div class="group-page-content-header">내 그룹</div>
        <GroupJoined v-if="groupList.length != 0" :groupList="groupList"/>
        <GroupNotFound v-else/>
      </section>
    </div>
  </div>
</template>

<script>
import { getAllGroup } from '@/api/group.js'
import GroupRecommend from "@/components/GroupFeed/GroupRecommend.vue";
import GroupJoined from '../../components/GroupFeed/GroupJoined.vue';
import GroupNotFound from '../../components/GroupFeed/GroupNotFound.vue';
import Sidebar from '../../components/Common/Sidebar.vue';
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
export default {
  components: { GroupRecommend, GroupJoined, GroupNotFound, Sidebar, NewsFeedHeader },
  data() {
    return {
      // groups: [{}, {}, {}, {}],
      groupList: [],
    };
  },
  created () {
    this.getGroupList();
  },
  methods: {
    getGroupList () {
      getAllGroup(
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
