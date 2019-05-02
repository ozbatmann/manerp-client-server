<template>
    <v-container fluid>
        <voyage-management-table></voyage-management-table>
    </v-container>
</template>

<script>
    import voyageManagementTable from '@/modules/main/voyage/components/incompleteVoyageManagementTable'

    export default {
        name: "VoyageManagement",
        components: {
            voyageManagementTable
        }
    }
</script>

<style scoped>

</style>