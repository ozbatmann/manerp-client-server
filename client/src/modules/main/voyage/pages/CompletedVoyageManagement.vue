<template>
    <v-container fluid>
        <voyage-management-table></voyage-management-table>
    </v-container>
</template>

<script>
    import voyageManagementTable from '@/modules/main/voyage/components/completedVoyageManagementTable'

    export default {
        name: "VoyageManagement",
        components: {
            voyageManagementTable
        }
    }
</script>

<style scoped>

</style>