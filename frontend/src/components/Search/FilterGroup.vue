<template>
  <div>
    <div class="filterGroupFrame">
      <div class="filterGroup">
        <div class="filterGroupImgFrame">
          <div class="filterGroupImg">
            <img src="@/assets/loverduck.png" alt="" />
          </div>
        </div>
        <!-- <div class="resultHeader">sss</div> -->
        <div class="filterGroupDetail">
          <div class="filterGroupDetailTitle">명도균</div>
          <div class="filterGroupDetailDescribe">
            <div>🏋🏻‍♂️🏡</div>
            <div>명도균님의 소식을 받고있습니다.</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    category: String,
  },
  data() {
    return {
      grouplist: [{}, {}, {}, {}],
      search_grouplist: [{}, {}, {}],
    };
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.filterGroupFrame {
  width: 686px;
  height: 115px;
  margin-bottom: 16px;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}
.filterGroup {
  width: 654px;
  height: 83px;
  margin: 16px;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
}

.filterGroupImgFrame {
  width: 60px;
  height: 83px;
  margin-right: 15px;
}

.filterGroupImg {
  width: 60px;
  height: 60px;
  margin-right: 15px;
  overflow: hidden;
  border-radius: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.filterGroupImg img {
  width: 80px;
  object-fit: cover;
}

.filterGroupDetail {
  width: 580px;
  height: 83px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
}
.filterGroupDetailTitle {
  font-size: 20px;
  font-weight: 700;
  text-align: left;
}

.filterGroupDetailDescribe {
  font-size: 16px;
  font-weight: 300;
  text-align: left;
}
</style>
