<template>
  <div>
    <div class="resultListFrame">
      <div class="resultCategoryTitle">{{ category }}</div>
      <div class="categorytitlehr"></div>
      <div class="resultContents">
        <!-- 컴포넌트 -->
        <GroupResult
          v-for="(grouplist, idx) in search_grouplist"
          :key="idx"
          :grouplist="grouplist"
        />
        <div
          :class="[grouplist.length <= 3 ? 'moresee_none' : '']"
          class="resultMoresee"
        >
          모두 보기
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import GroupResult from "./GroupResult.vue";
export default {
  components: { GroupResult },
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
.resultListFrame {
  width: 100%;
  height: 500px;
  margin-bottom: 16px;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.resultCategoryTitle {
  width: 100%;
  height: 36px;
  padding: 0 16px;
  margin-top: 20px;
  padding-bottom: 4px;
  font-size: 20px;
  font-weight: 700;
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
}
.categorytitlehr {
  width: 100%;
  height: 0.5px;
  background-color: rgba(17, 17, 17, 0.2);
}
.resultContents {
  width: 100%;
  height: 90%;
  margin: auto;
  padding: 16px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.resultMoresee {
  width: 100%;
  height: 45px;
  margin: auto;
  display: flex;
  justify-content: center;
  border-radius: 10px;
  align-items: center;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  border: none;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
  background-color: #d8dadf;
}
.moresee_none {
  display: none;
}
</style>
