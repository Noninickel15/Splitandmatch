package com.example.splitandmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
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
import java.util.Date
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MatchGreen,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Split & Match",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(CardBg)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MatchGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Für Studierende in Villingen-Schwenningen",
                    style = MaterialTheme.typography.labelSmall,
                    color = MatchGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Fußball schauen,\nPlätze teilen,\nFans matchen.",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 44.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Eine App-Idee, mit der WGs und Einzelpersonen ihre Streaming-Abos, freie Plätze und Spielwünsche eintragen – und passende Fußballabende in der Nähe finden.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onStartPrototype,
            colors = ButtonDefaults.buttonColors(containerColor = MatchGreen),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = "Prototyp testen",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onViewFeedback,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(16.dp),
            border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(width = 1.dp)
        ) {
            Text(
                text = "Feedbackfragen ansehen",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrototypeScreen(onBack: () -> Unit) {
    var wgName by remember { mutableStateOf("") }
    var abo by remember { mutableStateOf("DAZN") }
    var seats by remember { mutableStateOf("3") }
    var game by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(value = false) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

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
                    
                    val formatter = SimpleDateFormat("EEEE, HH:mm 'Uhr'", Locale.GERMANY)
                    date = formatter.format(cal.time)
                    showTimePicker = false
                }) { Text("OK", color = MatchGreen) }
            },
            containerColor = CardBg,
            title = { Text("Uhrzeit wählen", color = Color.White) },
            text = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TimePicker(state = timePickerState)
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 24.dp)
    ) {
        item {
            Text(
                text = "Mein Fußballabend",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            FormTextField(label = "WG oder Name", value = wgName, onValueChange = { wgName = it }, placeholder = "z. B. WG Neckarblick")
            Spacer(modifier = Modifier.height(16.dp))
            FormTextField(label = "Abo", value = abo, onValueChange = { abo = it }, placeholder = "DAZN, Sky, ...")
            Spacer(modifier = Modifier.height(16.dp))
            FormTextField(label = "Freie Plätze", value = seats, onValueChange = { seats = it }, placeholder = "3")
            Spacer(modifier = Modifier.height(16.dp))
            FormTextField(label = "Spiel, das ihr schauen wollt", value = game, onValueChange = { game = it }, placeholder = "z. B. BVB – Bayern")
            Spacer(modifier = Modifier.height(16.dp))
            
            // Clickable date field
            Box(modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true }) {
                FormTextField(
                    label = "Datum und Uhrzeit", 
                    value = date, 
                    onValueChange = {}, 
                    placeholder = "Tippen zum Auswählen",
                    enabled = false
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showResults = true },
                colors = ButtonDefaults.buttonColors(containerColor = MatchGreen),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Matches simulieren",
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

        if (showResults) {
            item {
                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Vorschläge",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "3 Treffer",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(dummyMatches) { match ->
                MatchCard(match)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            TextButton(onClick = onBack) {
                Text("Zurück zur Startseite", color = MatchGreen)
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun FeedbackScreen(onBack: () -> Unit) {
    val feedbackQuestions = listOf(
        "Ist sofort klar, wer wen einlädt und warum ein Match vorgeschlagen wird?",
        "Welche Angaben braucht die App, damit sich Gastgeber und Gäste sicher fühlen?",
        "Ist das Gegenseitigkeitsprinzip zwischen DAZN-, Sky- und anderen Abos nachvollziehbar?",
        "Welche Funktion sollte im nächsten Prototyp wirklich klickbar werden: Profil, Matching oder Chat?"
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
                text = "Worauf ihr beim Testen achten könnt",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Der Prototyp hilft, nicht nur die Idee zu erklären, sondern konkrete Rückmeldungen zu sammeln.",
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
                    text = "Deine Rückmeldung",
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
                        text = "Vielen Dank für dein Feedback! \uD83D\uDE4F",
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
                Text("Zurück zur Startseite", color = MatchGreen)
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
    val name: String,
    val description: String,
    val distance: String,
    val tags: List<String>,
    val matchPercentage: Int
)

val dummyMatches = listOf(
    FootballMatch(
        name = "WG Neckarblick",
        description = "Besitzt DAZN, schaut das Freitagsspiel und hat 3 Plätze frei. Passt gut für einen ersten Testlauf.",
        distance = "1,2 km",
        tags = listOf("Ruhige Runde"),
        matchPercentage = 92
    ),
    FootballMatch(
        name = "Einzelperson: Mina",
        description = "Würde gerne mitkommen, bringt Snacks mit und bevorzugt Gruppen mit 3-5 Personen.",
        distance = "0,8 km",
        tags = listOf("Bringt Snacks"),
        matchPercentage = 85
    ),
    FootballMatch(
        name = "Sportheim VS",
        description = "Öffentliches Schauen, Sky & DAZN vorhanden. Große Leinwand und viele Fans.",
        distance = "2,5 km",
        tags = listOf("Stimmung", "Getränke"),
        matchPercentage = 78
    )
)

@Composable
fun MatchCard(match: FootballMatch) {
    var isSent by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var requestMessage by remember { mutableStateOf("") }

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
                        placeholder = { Text("z. B. Habt ihr noch Platz für Snacks?", color = Color.Gray) },
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
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = match.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
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

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSent) MatchGreen.copy(alpha = 0.2f) else Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = !isSent
            ) {
                if (isSent) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MatchGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Anfrage gesendet", color = MatchGreen, fontWeight = FontWeight.Bold)
                } else {
                    Text("Anfrage senden", color = Color.Black, fontWeight = FontWeight.Bold)
                }
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
