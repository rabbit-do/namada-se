package net.namada.nebbstats.models

/**
 * @param subCategoryName: name of sub category of S Class
 * @param listPilotSubmissions: list of submissions of this subCategoryName
 * @param categoryPilotStat: statistic number in this category
 */
class ClassifiedSubmission (
    val subCategoryName: String,
    val listPilotSubmissions: List<Submission>,
    val categoryPilotStat: CategoryStat,
    val listCrewSubmissions: List<Submission>,
    val categoryCrewStat: CategoryStat
)