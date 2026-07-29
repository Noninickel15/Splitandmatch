package com.example.splitandmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.splitandmatch.ui.theme.CardBg
import com.example.splitandmatch.ui.theme.DarkBg
import com.example.splitandmatch.ui.theme.MatchGreen
import com.example.splitandmatch.ui.theme.SplitandmatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplitandmatchTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBg
                ) {
                    SplitAndMatchApp()
                }
            }
        }
    }
}

@Composable
fun SplitAndMatchApp() {
    var currentScreen by remember { mutableStateOf("home") }

    if (currentScreen != "home") {
        BackHandler {
            currentScreen = "home"
        }
    }

    when (currentScreen) {
        "home" -> HomeScreen(
            onStartPrototype = { currentScreen = "prototype" },
            onViewFeedback = { currentScreen = "feedback" }
        )
        "prototype" -> PrototypeScreen(onBack = { currentScreen = "home" })
        "feedback" -> FeedbackScreen(onBack = { currentScreen = "home" })
    }
}

@Composable
fun HomeScreen(onStartPrototype: () -> Unit, onViewFeedback: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Hintergrund-Element (Subtiles Design-Element oben rechts)
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 150.dp, y = (-100).dp)
                .clip(RoundedCornerShape(150.dp))
                .background(MatchGreen.copy(alpha = 0.05f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .navigationBarsPadding()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.Start
        ) {
            // Logo & Titel
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = MatchGreen
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Split & Match",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }

            // Hero Slogan
            Text(
                text = "Zusammen\nschauen.\nKosten teilen.\nFans matchen.",
                style = MaterialTheme.typography.displayMedium.copy(
                    lineHeight = 52.sp,
                    fontWeight = FontWeight.Black
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Beschreibung
            Text(
                text = "Finde passende Fu\u00DFballabende in deiner N\u00E4he oder biete selbst Pl\u00E4tze in deiner WG an. Einfach, sicher und gemeinsam.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.LightGray,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Standort-Badge (Subtil)
            Surface(
                color = CardBg,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MatchGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Villingen-Schwenningen",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }
            }

            // Haupt-Button
            Button(
                onClick = onStartPrototype,
                colors = ButtonDefaults.buttonColors(containerColor = MatchGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "JETZT STARTEN",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sekundärer Button (Feedback)
            TextButton(
                onClick = onViewFeedback,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Feedback zum Konzept geben",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Prototyp-Hinweis
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "App-Konzept \u2022 Interaktiver Prototyp",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.3f)
                )
            }
        }
    }
}

enum class UserRole { SEEKING, OFFERING }

val preferenceTags = listOf(
    "Snacks mitbringen",
    "Ruhige Runde",
    "Stimmung",
    "Beitrag 5 \u20AC",
    "Getr\u00E4nke vorhanden",
    "Raucherfrei"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrototypeScreen(onBack: () -> Unit) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    var wgName by remember { mutableStateOf("") }
    var abo by remember { mutableStateOf("DAZN") }
    var seats by remember { mutableStateOf("3") }
    var game by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var costSharing by remember { mutableStateOf("") }
    var selectedTags by remember { mutableStateOf(setOf<String>()) }
    var customTag by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(false) }
    var selectedMatchIds by remember { mutableStateOf(setOf<String>()) }
    
    var sortOrder by remember { mutableStateOf("Match %") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var showMatchInfoDialog by remember { mutableStateOf(false) }
    var showNotifications by remember { mutableStateOf(false) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = true)

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    showTimePicker = true
                }) { Text("Weiter", color = MatchGreen) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Abbrechen", color = Color.Gray) }
            },
            colors = DatePickerDefaults.colors(containerColor = CardBg)
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val cal = Calendar.getInstance()
                    datePickerState.selectedDateMillis?.let { cal.timeInMillis = it }
                    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    cal.set(Calendar.MINUTE, timePickerState.minute)

                    // 24-Stunden-Format und deutsches Datum
                    val formatter = SimpleDateFormat("EEEE, dd. MMMM, HH:mm 'Uhr'", Locale.GERMANY)
                    date = formatter.format(cal.time)
                    showTimePicker = false
                }) { Text("OK", color = MatchGreen) }
            },
            containerColor = CardBg,
            title = { Text("Uhrzeit w\u00E4hlen (24h)", color = Color.White) },
            text = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TimePicker(state = timePickerState)
                }
            }
        )
    }
    
    if (showMatchInfoDialog) {
        AlertDialog(
            onDismissRequest = { showMatchInfoDialog = false },
            containerColor = CardBg,
            title = { Text("Berechnung des Match-Werts", color = Color.White) },
            text = {
                Column {
                    Text("Der Wert setzt sich wie folgt zusammen:", color = Color.LightGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("\u2022 Gleiches Abo: +40%", color = Color.White)
                    Text("\u2022 Tag-\u00DCbereinstimmung: +10% pro Tag", color = Color.White)
                    Text("\u2022 R\u00E4umliche N\u00E4he: bis zu +30%", color = Color.White)
                }
            },
            confirmButton = {
                TextButton(onClick = { showMatchInfoDialog = false }) { Text("Verstanden", color = MatchGreen) }
            }
        )
    }

    Scaffold(
        topBar = {
            if (showResults) {
                CenterAlignedTopAppBar(
                    title = { Text("Ergebnisse", color = Color.White, style = MaterialTheme.typography.titleMedium) },
                    actions = {
                        IconButton(onClick = { showNotifications = !showNotifications }) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = if (showNotifications) MatchGreen else Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DarkBg)
                )
            }
        },
        containerColor = DarkBg
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            item {
                if (showResults) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showResults = false }) {
                            Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Suche bearbeiten", color = MatchGreen)
                        }
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Sortieren nach:", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                            TextButton(onClick = { sortOrder = if (sortOrder == "Match %") "Distanz" else "Match %" }) {
                                Text(sortOrder, color = MatchGreen)
                            }
                        }
                    }
                }
                
                if (showNotifications && showResults) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = MatchGreen.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MatchGreen)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Neue Nachricht von WG Neckarblick erhalten!", color = Color.White, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Text(
                    text = if (!showResults) {
                        if (selectedRole == null) "Was m\u00F6chtest du tun?" else "Mein Fu\u00DFballabend"
                    } else "Passende Matches",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (!showResults) {
                    Text(
                        text = if (selectedRole == null) {
                            "W\u00E4hle zuerst deine Rolle \u2013 so bleibt klar, ob du suchst oder anbietest."
                        } else if (selectedRole == UserRole.SEEKING) {
                            "Du suchst einen Ort zum Mitschauen."
                        } else {
                            "Du bietest Pl\u00E4tze bei dir an."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Basiert auf deinen Angaben.", color = Color.LightGray, style = MaterialTheme.typography.bodySmall)
                        IconButton(onClick = { showMatchInfoDialog = true }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MatchGreen, modifier = Modifier.size(16.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (!showResults) {
                if (selectedRole == null) {
                    item {
                        RoleChoiceCard(
                            title = "Ich suche einen Ort zum Schauen",
                            subtitle = "Finde WGs und Orte mit freien Pl\u00E4tzen in der N\u00E4he.",
                            onClick = {
                                selectedRole = UserRole.SEEKING
                                showResults = false
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        RoleChoiceCard(
                            title = "Ich biete Pl\u00E4tze bei mir an",
                            subtitle = "Trage dein Abo, freie Pl\u00E4tze und Hinweise f\u00FCr G\u00E4ste ein.",
                            onClick = {
                                selectedRole = UserRole.OFFERING
                                showResults = false
                            }
                        )
                    }
                } else {
                    item {
                        TextButton(
                            onClick = {
                                selectedRole = null
                                showResults = false
                                selectedTags = emptySet()
                            },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Rolle wechseln", color = MatchGreen)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        FormTextField(
                            label = if (selectedRole == UserRole.OFFERING) "WG oder Name" else "Dein Name",
                            value = wgName,
                            onValueChange = { wgName = it },
                            placeholder = if (selectedRole == UserRole.OFFERING) "z. B. WG Neckarblick" else "z. B. Mina"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        FormTextField(
                            label = if (selectedRole == UserRole.OFFERING) "Abo" else "Bevorzugtes Abo",
                            value = abo,
                            onValueChange = { abo = it },
                            placeholder = "DAZN, Sky, ..."
                        )

                        if (selectedRole == UserRole.OFFERING) {
                            Spacer(modifier = Modifier.height(16.dp))
                            FormTextField(
                                label = "Freie Pl\u00E4tze",
                                value = seats,
                                onValueChange = { seats = it },
                                placeholder = "3"
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            FormTextField(
                                label = "Kostenbeteiligung",
                                value = costSharing,
                                onValueChange = { costSharing = it },
                                placeholder = "z. B. 5 \u20AC f\u00FCr Snacks"
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        FormTextField(
                            label = if (selectedRole == UserRole.OFFERING) {
                                "Spiel, das ihr schauen wollt"
                            } else {
                                "Spiel, das du schauen willst"
                            },
                            value = game,
                            onValueChange = { game = it },
                            placeholder = "z. B. BVB \u2013 Bayern"
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true }) {
                            FormTextField(
                                label = "Datum und Uhrzeit",
                                value = date,
                                onValueChange = {},
                                placeholder = "Tippen zum Ausw\u00E4hlen",
                                enabled = false
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = if (selectedRole == UserRole.OFFERING) {
                                "Hinweise f\u00FCr G\u00E4ste"
                            } else {
                                "Deine Pr\u00E4ferenzen"
                            },
                            color = Color.LightGray,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (selectedRole == UserRole.OFFERING) {
                                "Was sollen G\u00E4ste wissen? W\u00E4hle passende Tags."
                            } else {
                                "Was bringst du mit oder suchst du? W\u00E4hle passende Tags."
                            },
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        PreferenceTagRow(
                            tags = preferenceTags,
                            selectedTags = selectedTags,
                            onToggle = { tag ->
                                selectedTags = if (tag in selectedTags) {
                                    selectedTags - tag
                                } else {
                                    selectedTags + tag
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = customTag,
                                onValueChange = { customTag = it },
                                placeholder = { Text("Eigener Tag...", color = Color.Gray) },
                                modifier = Modifier.weight(1f),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = CardBg,
                                    unfocusedContainerColor = CardBg,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedIndicatorColor = MatchGreen,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    if (customTag.isNotBlank()) {
                                        selectedTags = selectedTags + customTag.trim()
                                        customTag = ""
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MatchGreen)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Hinzuf\u00FCgen",
                                    tint = Color.Black
                                )
                            }
                        }

                        if (selectedTags.any { it !in preferenceTags }) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Deine eigenen Tags:",
                                color = Color.Gray,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            PreferenceTagRow(
                                tags = selectedTags.filter { it !in preferenceTags },
                                selectedTags = selectedTags,
                                onToggle = { tag ->
                                    selectedTags = selectedTags - tag
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        InfoBox(
                            icon = Icons.Default.Info,
                            title = "Datenschutz",
                            text = "Deine Daten werden nur f\u00FCr das Matching verwendet und nicht an Dritte weitergegeben."
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoBox(
                            icon = Icons.Default.Warning,
                            title = "Sicherheit",
                            text = "Genaue Adressen werden erst nach gegenseitiger Best\u00E4tigung im Chat geteilt."
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { showResults = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MatchGreen),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (selectedRole == UserRole.SEEKING) {
                                    "Passende Matches finden"
                                } else {
                                    "Passende G\u00E4ste finden"
                                },
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            } else {
                val results = if (selectedRole == UserRole.SEEKING) dummyHostMatches else dummyGuestMatches
                val sortedResults = if (sortOrder == "Match %") results.sortedByDescending { it.matchPercentage } else results.sortedBy { it.distance }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = if (selectedRole == UserRole.SEEKING) "Vorschl\u00E4ge" else "Interessierte",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${results.size} Treffer",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (selectedRole == UserRole.SEEKING && selectedMatchIds.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* In einem echten App-Context w\u00FCrden hier alle Anfragen gesendet */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MatchGreen)
                        ) {
                            Text("${selectedMatchIds.size} Gruppen gleichzeitig anfragen", color = Color.Black)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(sortedResults) { match ->
                    MatchCard(
                        match = match,
                        isSelected = match.id in selectedMatchIds,
                        onSelectToggle = {
                            selectedMatchIds = if (match.id in selectedMatchIds) {
                                selectedMatchIds - match.id
                            } else {
                                selectedMatchIds + match.id
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                TextButton(onClick = onBack) {
                    Text("Zur\u00FCck zur Startseite", color = MatchGreen)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun InfoBox(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(icon, contentDescription = null, tint = MatchGreen, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
            Text(text, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun RoleChoiceCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = subtitle,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MatchGreen
            )
        }
    }
}

@Composable
fun PreferenceTagRow(
    tags: List<String>,
    selectedTags: Set<String>,
    onToggle: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        tags.chunked(2).forEach { rowTags ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowTags.forEach { tag ->
                    val selected = tag in selectedTags
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (selected) MatchGreen.copy(alpha = 0.2f)
                                else Color.White.copy(alpha = 0.08f)
                            )
                            .clickable { onToggle(tag) }
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = tag,
                            color = if (selected) MatchGreen else Color.LightGray,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
                if (rowTags.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun FeedbackScreen(onBack: () -> Unit) {
    val feedbackQuestions = listOf(
        "Ist sofort klar, wer wen einl\u00E4dt und warum ein Match vorgeschlagen wird?",
        "Welche Angaben braucht die App, damit sich Gastgeber und G\u00E4ste sicher f\u00FChlen?",
        "Ist das Gegenseitigkeitsprinzip zwischen DAZN-, Sky- und anderen Abos nachvollziehbar?",
        "Welche Funktion sollte im n\u00E4chsten Prototyp wirklich klickbar werden: Profil, Matching oder Chat?"
    )

    var feedbackText by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 24.dp)
    ) {
        item {
            Text(
                text = "Worauf ihr beim Testen achten k\u00F6nnt",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Der Prototyp hilft, nicht nur die Idee zu erkl\u00E4ren, sondern konkrete R\u00FCckmeldungen zu sammeln.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        items(feedbackQuestions) { question ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CardBg)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MatchGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = question,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            if (!isSubmitted) {
                Text(
                    text = "Deine R\u00FCckmeldung",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    placeholder = { Text("Schreib uns hier deine Meinung...", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CardBg,
                        unfocusedContainerColor = CardBg,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = MatchGreen,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { if (feedbackText.isNotBlank()) isSubmitted = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MatchGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Feedback senden", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MatchGreen.copy(alpha = 0.1f))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Vielen Dank f\u00FCr dein Feedback! \uD83D\uDE4F",
                        color = MatchGreen,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zur\u00FCck zur Startseite", color = MatchGreen)
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun FormTextField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String, enabled: Boolean = true) {
    Column {
        Text(text = label, color = Color.LightGray, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CardBg,
                unfocusedContainerColor = CardBg,
                disabledContainerColor = CardBg,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

data class FootballMatch(
    val id: String,
    val name: String,
    val description: String,
    val distance: String,
    val tags: List<String>,
    val matchPercentage: Int,
    val isVerified: Boolean = false
)

val dummyHostMatches = listOf(
    FootballMatch(
        id = "1",
        name = "WG Neckarblick",
        description = "Besitzt DAZN, schaut das Freitagsspiel und hat 3 Pl\u00E4tze frei. Passt gut f\u00FCr einen ersten Testlauf.",
        distance = "1,2 km",
        tags = listOf("Ruhige Runde"),
        matchPercentage = 92,
        isVerified = true
    ),
    FootballMatch(
        id = "2",
        name = "Sportheim VS",
        description = "\u00D6ffentliches Schauen, Sky & DAZN vorhanden. Gro\u00DFe Leinwand und viele Fans.",
        distance = "2,5 km",
        tags = listOf("Stimmung", "Getr\u00E4nke vorhanden"),
        matchPercentage = 78,
        isVerified = false
    ),
    FootballMatch(
        id = "3",
        name = "WG Schwenninger Stra\u00DFe",
        description = "Sky-Abo, zwei freie Pl\u00E4tze. G\u00E4ste sollen Snacks mitbringen, kleine Beteiligung erw\u00FCnscht.",
        distance = "1,8 km",
        tags = listOf("Snacks mitbringen", "Beitrag 5 \u20AC"),
        matchPercentage = 74,
        isVerified = true
    ),
    FootballMatch(
        id = "4",
        name = "Fritz-Erler-Siedlung",
        description = "Gem\u00FCtliches Schauen bei uns, haben Sky. Wir kochen zusammen davor.",
        distance = "0,5 km",
        tags = listOf("Getr\u00E4nke vorhanden", "Stimmung"),
        matchPercentage = 88,
        isVerified = false
    ),
    FootballMatch(
        id = "5",
        name = "Studentenwohnheim Schwenningen",
        description = "Gro\u00DFer Gemeinschaftsraum, Beamer, DAZN. Alle sind willkommen!",
        distance = "3,1 km",
        tags = listOf("Stimmung"),
        matchPercentage = 65,
        isVerified = true
    )
)

val dummyGuestMatches = listOf(
    FootballMatch(
        id = "g1",
        name = "Mina",
        description = "W\u00FCrde gerne mitkommen, bringt Snacks mit und bevorzugt Gruppen mit 3\u20135 Personen.",
        distance = "0,8 km",
        tags = listOf("Snacks mitbringen", "Ruhige Runde"),
        matchPercentage = 85,
        isVerified = true
    ),
    FootballMatch(
        id = "g2",
        name = "Jonas",
        description = "Sucht einen Ort f\u00FCr das Topspiel, zahlt gerne einen Beitrag und bleibt eher ruhig.",
        distance = "1,1 km",
        tags = listOf("Beitrag 5 \u20AC", "Ruhige Runde"),
        matchPercentage = 81,
        isVerified = false
    ),
    FootballMatch(
        id = "g3",
        name = "Lea & Tom",
        description = "Kommen zu zweit, bringen Getr\u00E4nke mit und freuen sich auf Stimmung.",
        distance = "2,0 km",
        tags = listOf("Stimmung", "Getr\u00E4nke vorhanden"),
        matchPercentage = 76,
        isVerified = true
    ),
    FootballMatch(
        id = "g4",
        name = "Lukas",
        description = "Riesen BVB Fan, sucht Anschluss f\u00FCr Samstagsspiele. Bringt gute Laune mit.",
        distance = "1,5 km",
        tags = listOf("Stimmung"),
        matchPercentage = 89,
        isVerified = true
    ),
    FootballMatch(
        id = "g5",
        name = "Sarah",
        description = "Schaut gerne entspannt Fu\u00DFball, am liebsten mit k\u00FChlen Getr\u00E4nken.",
        distance = "2,2 km",
        tags = listOf("Getr\u00E4nke vorhanden", "Ruhige Runde"),
        matchPercentage = 72,
        isVerified = false
    )
)

@Composable
fun MatchCard(
    match: FootballMatch,
    isSelected: Boolean = false,
    onSelectToggle: () -> Unit = {}
) {
    var isSent by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var requestMessage by remember { mutableStateOf("") }
    var showReportDialog by remember { mutableStateOf(false) }

    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            containerColor = CardBg,
            title = { Text("Nutzer melden", color = Color.White) },
            text = { Text("M\u00F6chtest du diesen Nutzer aufgrund von unangemessenem Verhalten melden?", color = Color.LightGray) },
            confirmButton = {
                TextButton(onClick = { showReportDialog = false }) {
                    Text("Melden", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showReportDialog = false }) {
                    Text("Abbrechen", color = Color.LightGray)
                }
            }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = CardBg,
            title = {
                Text(
                    text = "Anfrage an ${match.name}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Stelle eine kurze Frage oder schreib eine Nachricht an den Gastgeber:",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = requestMessage,
                        onValueChange = { requestMessage = it },
                        placeholder = { Text("z. B. Habt ihr noch Platz f\u00FCr Snacks?", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = DarkBg,
                            unfocusedContainerColor = DarkBg,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = MatchGreen,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hinweis: Bei kurzfristiger Absage bitte fr\u00FChzeitig Bescheid geben!",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isSent = true
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MatchGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Absenden", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Abbrechen", color = Color.LightGray)
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectToggle() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CardBg.copy(alpha = 0.8f) else CardBg
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MatchGreen) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = match.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    if (match.isVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verifiziert",
                            tint = MatchGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MatchGreen.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${match.matchPercentage}% Match",
                            color = MatchGreen,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { showReportDialog = true }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Warning, contentDescription = "Melden", tint = Color.Gray, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = match.description,
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Badge(match.distance)
                match.tags.forEach { tag ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(tag)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSent) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MatchGreen.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(8.dp),
                        enabled = false
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MatchGreen, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Angefragt", color = MatchGreen, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = { isSent = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("L\u00F6schen", color = Color.Red.copy(alpha = 0.5f))
                    }
                }
            } else {
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Anfrage senden", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            if (isSelected) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ausgew\u00E4hlt f\u00FCr Sammelanfrage",
                    color = MatchGreen,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun Badge(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.LightGray, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SplitandmatchTheme(darkTheme = true) {
        Surface(color = DarkBg) {
            SplitAndMatchApp()
        }
    }
}
