# 🌱 PlantBuddy

## 📖 Opis
**PlantBuddy** je mobilna aplikacija, namenjena vrtnarjem vseh ravni izkušenj. 🌼 Aplikacija ponuja nasvete in navodila za sajenje ter nego različnih vrst rastlin. Z uporabo vgrajenega zemljevida 🗺 lahko uporabniki najdejo najbližje trgovine za vrtnarstvo in skupnostne vrtove. PlantBuddy prav tako pošilja potisna sporočila 📬, ki uporabnike opominjajo na pomembne vrtnarske aktivnosti, kot so zalivanje, gnojenje in obrezovanje.

## 🌟 Funkcije
- **Iskanje Lokacij**: Najdite najbližje trgovine za vrtnarstvo in skupnostne vrtove. 📍
- **Opomniki za Nego Rastlin**: Prejemajte potisna sporočila za zalivanje, gnojenje in druge naloge. 💦
- **Nasveti za Vrtnarjenje**: Koristni nasveti za nego različnih vrst rastlin. 🌿

## 👤 Avtor
Matic L. (mlukee)

## 💻 Tehnologije
Aplikacija je razvita z uporabo:
- **Kotlin**: Glavni programski jezik. 👨‍💻
- **Android Studio**: Razvojno okolje za izdelavo Android aplikacij. 📱
- **Google Maps API**: Za vključevanje zemljevidov in lokacijskih funkcij. 🗺️

## Posnetki zaslonov
| Domača stran | Domača stran z rastlinami | Vnos/Posodabljanje rastline | Prikaz trgovin na zemljevidu | Podrobnosti posamezne trgovine |
|--------------|---------------------------|----------------------------|-----------------------------|--------------------------------|
| <img src="https://github.com/mlukee/plantBuddyMobileApp/assets/31586745/47680aae-e164-4ab5-8e9c-a3486bf54d02" width="220"> | <img src="https://github.com/mlukee/plantBuddyMobileApp/assets/31586745/99c94e9e-39d2-4cb7-bc70-9f85048d95b2" width="180"> | <img src="https://github.com/mlukee/plantBuddyMobileApp/assets/31586745/362ecdb2-799b-4645-9700-5215637320e3" width="180"> | <img src="https://github.com/mlukee/plantBuddyMobileApp/assets/31586745/50c44b1a-5593-4a40-b383-f874c1e9c903" width="180"> | <img src="https://github.com/mlukee/plantBuddyMobileApp/assets/31586745/e13bc3b2-bc2c-4f1d-a5e2-50b896aeeff4" width="180"> |


# [Room](https://developer.android.com/jetpack/androidx/releases/room)
**Room** je knjižnica za mapiranje objektov v podatkovne zbirke, je abstraktni sloj nad SQLite, ter omogoča enostaven dostop do podatkovne zbirke v Android aplikacijah. Razvili so jo pri Googlu.

## Zakaj Room?
Kadar pišemo SQL stavke, nas avtomatsko opozori, da imamo napako v sintaksi(uporaba napačne tabele, stolpec ne obstaja, ...). Room pod havbo tudi skrbi za pretvarjanje SQLite kode v DTO(Data Transfer Object).

## Prednosti in slabosti
| PREDNOSTI | SLABOSTI |
------------|-----------|
|Varnost tipov(*ang. type safety*) | Deluje le s SQLite |
|Omogoča enostavno integracijo s Kotlinovimi korutinami | Pri bolj zapletenih poizvedbah, lahko vodi do manjše zmogljivosti |
|Podpora za LiveData, kar omogoča, da se glede na spremembe UI samodejno posodobi | |
|Dobro napisana dokumentacija| |

## [Licenca](https://source.android.com/license)
Licenca je Apache 2.0.

## Informacije
 - **Zadnja posodobitev**: 29. november 2023, verzija 2.6.1
 - **Vzrževanje**: Knjižnica se posodablja redno - vsaj enkrat na mesec ali na dva meseca
 - **[Alternative](https://stackshare.io/android-room)**: DBFlow, Firebase, Realm, GreenDAO, SQLite

## Primer uporabe
Najprej ustvarimo **Entiteto**. Entiteta je razred, kateri predstavlja tabelo v podatkovni bazi.

```kotlin
@Parcelize
@Entity(tableName = "plant_table")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var plantType: String,
    var wateringSchedule: String,
    var lastWatering: Long,
) : Parcelable
```

Nato ustvarimo DAO - Data Access Object, kateri služi za dostop do podatkov v podatkovni bazi. Room generira kodo, potrebno za dostop do podatkovne baze, na podlagi metod, ki jih določimo v DAO.
```kotlin
@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlant(plant: Plant)

    @Update
    fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("DELETE FROM plant_table")
    suspend fun deleteAllPlants()

    @Query("SELECT * FROM plant_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Plant>>
}
```

Da bomo lahko to uporabljali, še moramo ustvariti podatkovno bazo.
```kotlin
@Database(entities = [Plant::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
```




