<template>
    <completed-voyage-management-table></completed-voyage-management-table>
</template>

<script>
    import completedVoyageManagementTable from '@/modules/main/voyage/components/completedVoyageManagementTable'

    export default {
        name: "VoyageManagement",
        components: {
            completedVoyageManagementTable
        }
    }
</script>

<style scoped>

</style>