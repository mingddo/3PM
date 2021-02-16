<template>
  <div class="filterGroupFrame" @click="gotoProfile">
    <div class="filterGroup">
      <div class="filterGroupImgFrame">
        <div class="filterGroupImg" @click="gotoProfile">
          <img
            class="filterGroupImg-img"
            v-if="grouplist.img"
            :src="`https://dtbqjjy7vxgz8.cloudfront.net/${grouplist.img}`"
            alt=""
          />
          <img
            v-else
            src="http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
            alt="유저프로필이미지"
            class="filterGroupImg-img"
          />
        </div>
      </div>
      <!-- <div class="resultHeader">sss</div> -->
      <div class="filterGroupDetail">
        <div class="filterGroupDetailTitle">{{ grouplist.nickname }}</div>
        <!-- <div class="filterGroupDetailDescribe">
          <div>🏋🏻‍♂️🏡</div>
          <div>명도균님의 소식을 받고있습니다.</div>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    grouplist: Object,
  },
  data() {
    return {
      search_grouplist: [{}, {}, {}],
    };
  },
  methods: {
    gotoProfile() {
      this.$router.push({ name: "MyPage", query: { name: this.grouplist.id } });
    },
  },
};
</script>

<style scoped></style>
