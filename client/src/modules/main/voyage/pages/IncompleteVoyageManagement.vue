<template>
    <v-container fluid>
        <incomplete-voyage-management-table></incomplete-voyage-management-table>
    </v-container>
</template>

<script>
    import incompleteVoyageManagementTable from '@/modules/main/voyage/components/incompleteVoyageManagementTable'

    export default {
        name: "VoyageManagement",
        components: {
            incompleteVoyageManagementTable
        }
    }
</script>

<style scoped>

</style>