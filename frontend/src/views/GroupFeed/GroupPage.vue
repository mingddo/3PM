<template>
  <div>
    <h1>핵인싸</h1>
    <GroupRecommend />
    <div @click="goto_create_group">새 글쓰기 +</div>
    <GroupCard v-for="(group, idx) in groups" :key="idx" :group="group" />
  </div>
</template>

<script>
import GroupRecommend from "@/components/GroupFeed/GroupRecommend.vue";
import GroupCard from "../../components/GroupFeed/GroupCard.vue";
export default {
  components: { GroupRecommend, GroupCard },
  data() {
    return {
      groups: [{}, {}, {}, {}],
    };
  },
  methods: {
    goto_create_group() {
      this.$router.push({ name: "groupform" });
    },
  },
};
</script>

<style></style>
