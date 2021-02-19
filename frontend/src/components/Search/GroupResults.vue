<template>
  <div>
    <div class="Groups-resultListFrame">
      <div class="Groups-resultCategoryTitle">{{ category }}</div>
      <div class="Groups-categorytitlehr"></div>
      <div class="Groups-resultContents">
        <!-- 컴포넌트 -->
        <span v-if="check_category === 1" class="full-width">
          <GroupResult
            v-for="(grouplist, idx) in search_grouplist"
            :key="idx"
            :grouplist="grouplist"
          />
        </span>
        <span v-if="check_category === 2" class="full-width">
          <FilterGroup
            v-for="(grouplist, idx) in search_grouplist"
            :key="idx"
            :grouplist="grouplist"
          />
        </span>
        <div
          :class="[valid_moresee ? '' : 'Groups-moresee_none']"
          class="Groups-resultMoresee"
        >
          모두 보기
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FilterGroup from "./FilterGroup.vue";
import GroupResult from "./GroupResult.vue";
export default {
  components: { GroupResult, FilterGroup },

  props: {
    category: String,
    groupresults: Array,
  },
  data() {
    return {
      grouplist: [{}, {}, {}, {}],
      search_grouplist: [],
      valid_moresee: true,
      check_category: null,
    };
  },
  methods: {
    is_valid() {
      if (this.groupresults.length > 3) {
        this.valid_moresee = true;
      } else {
        this.valid_moresee = false;
      }
    },
    copyResults() {
      this.search_grouplist = this.groupresults.slice(0, 3);
    },
    checkCategory() {
      if (this.category === "그룹") {
        this.check_category = 1;
      } else {
        this.check_category = 2;
      }
    },
  },
  mounted() {
    this.checkCategory();
    this.is_valid();
    this.copyResults();
  },
};
</script>

<style>
.full-width {
  width: 100%;
}

.Groups-resultListFrame {
  width: 100%;
  height: auto;
  margin-bottom: 16px;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.Groups-resultCategoryTitle {
  width: 100%;
  height: 36px;
  padding: 0 16px;
  margin-top: 10px;
  padding-bottom: 4px;
  font-size: 20px;
  font-weight: 700;
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
}
.Groups-categorytitlehr {
  width: 100%;
  height: 0.5px;
  background-color: rgba(17, 17, 17, 0.2);
}
.Groups-resultContents {
  width: 100%;
  height: 90%;
  margin: auto;
  padding: 16px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.Groups-resultMoresee {
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
.Groups-moresee_none {
  display: none;
}
</style>
