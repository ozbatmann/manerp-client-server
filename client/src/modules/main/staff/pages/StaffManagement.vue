<template>
    <v-container fluid>
        <staff-management-table></staff-management-table>
    </v-container>
</template>

<script>
    import staffManagementTable from '@/modules/main/staff/components/staffManagementTable'

    export default {
        name: "StaffManagement",
        components: {
            staffManagementTable
        }
    }
</script>

<style scoped>

</style>