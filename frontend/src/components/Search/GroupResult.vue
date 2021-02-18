<template>
  <div class="Group-ResultFrame" @click="gotogroupDetail">
    <div class="Group-resultImgFrame">
      <img
        v-if="grouplist.groupImg"
        :src="`https://dtbqjjy7vxgz8.cloudfront.net/${grouplist.groupImg}`"
        alt=""
        class="Group-resultImgFrame-img"
      />
      <img v-else src="@/assets/img/profileFM.svg" alt="" />
    </div>
    <div class="Group-reultDetail">
      <!-- 글 내용 -->
      <div class="Group-rsultDetailTilte">{{ grouplist.name }}</div>
      <div class="Group-resultDetailcontent">
        {{ grouplist.description }}
      </div>
      <div class="Group-resultDetailcnt">
        <i class="fas fa-users"></i>{{ grouplist.memberCnt }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    grouplist: Object,
  },
  data() {
    return {};
  },
  methods: {
    gotogroupDetail() {
      this.$router.push({
        name: "groupdetail",
        query: { groupId: this.grouplist.id },
      });
    },
  },
  mounted() {
  },
};
</script>

<style scoped></style>
