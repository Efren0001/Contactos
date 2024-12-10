package com.example.contactos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.contactos.ui.theme.ContactosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Contact(
    val name: String,
    val phone: String
)

val contacts = listOf(
    Contact("Alba", "+34 612 34 56 78"),
    Contact("Benito", "+34 623 45 67 89"),
    Contact("Carlos", "+34 634 56 78 90"),
    Contact("David", "+34 645 67 89 01"),
    Contact("Elena", "+34 656 78 90 12"),
    Contact("Fernando", "+34 667 89 01 23"),
    Contact("Gloria", "+34 678 90 12 34"),
    Contact("Héctor", "+34 689 01 23 45")
)

fun groupContactsByInitial(contacts: List<Contact>): List<Pair<Char, List<Contact>>> {
    return contacts
        .groupBy { it.name.first() }
        .toSortedMap()
        .map { it.key to it.value }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen() {
    val groupedContacts = remember { groupContactsByInitial(contacts) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contactos", textAlign = TextAlign.Center) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                groupedContacts.forEach { (initial, group) ->
                    item {
                        StickyHeader(initial)
                    }
                    items(group) { contact ->
                        ContactItem(contact)
                    }
                }
            }
        }
    )
}

@Composable
fun StickyHeader(initial: Char) {
    Text(
        text = "$initial",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(8.dp),
        color = Color.White
    )
}

@Composable
fun ContactItem(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Text(
            text = contact.name,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = contact.phone,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    ContactListScreen()
}

@Composable
fun GreetingPreview() {
    ContactosTheme {
        ContactListScreen()
    }
}