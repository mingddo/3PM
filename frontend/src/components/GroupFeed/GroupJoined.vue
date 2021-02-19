<template>
  <div>
    <GroupCard v-for="(group, idx) in groupList" :key="idx" :group="group"/>
  </div>
</template>

<script>
import GroupCard from './GroupCard.vue';
export default {
  components: { GroupCard },
  name: 'GroupJoined',
  props: {
    groupList: Array,
  },
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    
  },
};
</script>

<style lang="scss" scoped>

</style>