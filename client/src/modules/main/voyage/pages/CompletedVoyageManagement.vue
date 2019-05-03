<template>
    <v-container fluid>
        <completed-voyage-management-table></completed-voyage-management-table>
    </v-container>
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