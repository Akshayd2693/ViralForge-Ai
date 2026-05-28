package com.example.ui

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.CreatorHistoryItem
import com.example.data.GeminiService
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ViralForgeApp(viewModel: ViralForgeViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Futuristic floating gradient ambient particles
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(NeonPurple.copy(alpha = 0.08f), Color.Transparent),
                            center = Offset(size.width * 0.2f, size.height * 0.2f),
                            radius = size.width * 0.6f
                        )
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(NeonPink.copy(alpha = 0.05f), Color.Transparent),
                            center = Offset(size.width * 0.8f, size.height * 0.7f),
                            radius = size.width * 0.7f
                        )
                    )
                }
        )

        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            },
            label = "ScreenTransition"
        ) { screen ->
            when (screen) {
                ViralForgeScreen.LANDING -> LandingScreen(viewModel)
                ViralForgeScreen.LOGIN -> LoginScreen(viewModel)
                else -> MainAppShell(viewModel, screen)
            }
        }
    }
}

// ==================================================
// LANDING PAGE (SaaS Visual Presentation)
// ==================================================
@Composable
fun LandingScreen(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Landing Top Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(NeonPurple, NeonPink)),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "ViralForge",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "AI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    modifier = Modifier
                        .padding(start = 4.dp, top = 2.dp)
                        .border(1.dp, NeonCyan, RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }

            Button(
                onClick = { viewModel.currentScreen.value = ViralForgeScreen.LOGIN },
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, DarkBorder),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Login", color = TextPrimary)
            }
        }

        // Hero Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Neon badge
            Box(
                modifier = Modifier
                    .background(NeonPurple.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                    .border(1.dp, NeonPurple.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "THE FUTURE OF YOUTUBE VIRALITY",
                        color = NeonCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Title
            Text(
                text = "Forge Videos\nThat Explode",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 48.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, TextPrimary, NeonPurpleGlow)
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "ViralForge AI uses master psychological triggers, thumbnail heatmap prediction, and retention scripting to hyper-accelerate your channel growth.",
                color = TextSecondary,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action CTAs
            Button(
                onClick = { viewModel.currentScreen.value = ViralForgeScreen.LOGIN },
                colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(16.dp, ambientColor = NeonPurple, spotColor = NeonPurple)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Enter AI Workspace", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "✓ No credit card required  •  ✓ Join 12,000+ top creators",
                color = TextMuted,
                fontSize = 11.sp
            )
        }

        // Creator Stats Overview Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("4.8x", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                Text("Avg CTR Jump", fontSize = 12.sp, color = TextSecondary)
            }
            Box(modifier = Modifier.width(1.dp).height(36.dp).background(DarkBorder))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("1.2M", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = NeonPurpleGlow)
                Text("Scripts Written", fontSize = 12.sp, color = TextSecondary)
            }
            Box(modifier = Modifier.width(1.dp).height(36.dp).background(DarkBorder))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("94%", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = NeonPink)
                Text("Accuracy Score", fontSize = 12.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Features Showcase Header
        Text(
            text = "AI Suite Powered for Virality",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Text(
            text = "Engineered models replacing outdated guesswork with precise neuroscience analytics.",
            fontSize = 13.sp,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Large Premium Showcase Horizontal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            LandingFeatureCard(
                icon = Icons.Default.Title,
                title = "AI Viral Title Synthesizer",
                desc = "Analyzes high-CTR title formulas, emotional friction score, and triggers instant click-urgency.",
                accent = NeonPurple
            )
            LandingFeatureCard(
                icon = Icons.Default.Image,
                title = "AI Thumbnail Eye Analyzer",
                desc = "Predicts CTR metrics on uploaded assets using a simulated heatmap gaze network.",
                accent = NeonCyan
            )
            LandingFeatureCard(
                icon = Icons.Default.TrendingUp,
                title = "Why My Video Failed?",
                desc = "Input video URLs to diagnose retention drops, pacing gaps, and cliffhanger drops.",
                accent = NeonPink
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // PRICING SECTION
        Text(
            text = "Flexible Creator Tiers",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Text(
            text = "Invest in your channel velocity with professional creator growth blueprints.",
            fontSize = 13.sp,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Creator Pro Card (Featured Tier)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(
                    brush = Brush.verticalGradient(listOf(DarkSurface, DarkCard)),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    BorderStroke(1.5.dp, Brush.linearGradient(listOf(NeonPurple, NeonPink))),
                    RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("CREATOR PRO", color = NeonPurpleGlow, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Box(
                        modifier = Modifier
                            .background(NeonPink, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("MOST POPULAR", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("$49", fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
                    Text("/month", color = TextSecondary, modifier = Modifier.padding(bottom = 6.dp, start = 4.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Complete access to all extreme visual analytics models, unlimited script blocks, and competitor database insights.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                
                PricingCheckItem("Unlimited AI Viral Titles")
                PricingCheckItem("Direct 2K Thumbnail heatmaps")
                PricingCheckItem("Unlimited Retentive Scriptsizer")
                PricingCheckItem("10 Competitor intelligence channels")
                PricingCheckItem("Weekly priority AI growth tips")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.currentScreen.value = ViralForgeScreen.LOGIN },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get Started Instant", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Free Plan Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(DarkSurface, RoundedCornerShape(20.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column {
                Text("STARTER FREE", color = TextSecondary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("$0", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
                    Text("/month", color = TextSecondary, modifier = Modifier.padding(bottom = 4.dp, start = 4.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Test the waters of AI viral generation with basic features.", color = TextMuted, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.currentScreen.value = ViralForgeScreen.LOGIN },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, DarkBorder),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Join Core Suite", color = TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun LandingFeatureCard(icon: ImageVector, title: String, desc: String, accent: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSurface, RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, ArrayListColor(accent)), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(accent.copy(alpha = 0.15f), CircleShape)
                .border(1.dp, accent.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = accent, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = desc, fontSize = 12.sp, color = TextSecondary, lineHeight = 16.sp)
        }
    }
}

fun ArrayListColor(color: Color): Color {
    return color.copy(alpha = 0.15f)
}

@Composable
fun PricingCheckItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Check",
            tint = NeonCyan,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = TextPrimary, fontSize = 12.sp)
    }
}

// ==================================================
// AUTHENTICATION SCREEN (Simulated SaaS Entry point)
// ==================================================
@Composable
fun LoginScreen(viewModel: ViralForgeViewModel) {
    var email by remember { mutableStateFlowOf("") }
    var password by remember { mutableStateFlowOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(24.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Return Arrow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { viewModel.currentScreen.value = ViralForgeScreen.LANDING }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Back", tint = TextSecondary)
                    }
                }
                
                // Icon Header
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            brush = Brush.radialGradient(listOf(NeonPurple, Color.Transparent)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Secured",
                        tint = NeonPurpleGlow,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Welcome to ViralForge",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Enter credentials to unlock analytics workspace.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email input
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Creator Email") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = NeonPurple,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Password input
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Access Code") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = NeonPurple,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (email.isNotBlank()) viewModel.performLogin(email)
                        else Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show()
                    })
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (email.isNotBlank()) viewModel.performLogin(email)
                        else viewModel.performLogin("starcreator@youtube.com")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Enter Workspace", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Google sign-in simulation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                        .clickable { viewModel.performLogin("spikecreator@gmail.com") }
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "SSO",
                        tint = NeonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Sign in with Google", color = TextPrimary, fontSize = 13.sp)
                }
            }
        }
    }
}

fun <T> rememberStateFlow(flow: kotlinx.coroutines.flow.MutableStateFlow<T>): MutableState<T> {
    return mutableStateOf(flow.value)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> mutableStateFlowOf(value: T): MutableState<T> = mutableStateOf(value)


// ==================================================
// MAIN APP WORKSPACE SHELL (Sidebar Navigation + Container)
// ==================================================
@Composable
fun MainAppShell(viewModel: ViralForgeViewModel, activeScreen: ViralForgeScreen) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkBg,
                modifier = Modifier.width(300.dp)
            ) {
                SidebarMenuContent(viewModel, activeScreen) {
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkBg)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = getScreenTitle(activeScreen),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    // Key Status indicators
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val keyAvailable = GeminiService.isKeyAvailable()
                        Box(
                            modifier = Modifier
                                .background(
                                    if (keyAvailable) NeonGreen.copy(alpha = 0.15f) else NeonPink.copy(alpha = 0.15f),
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    1.dp,
                                    if (keyAvailable) NeonGreen.copy(alpha = 0.4f) else NeonPink.copy(alpha = 0.4f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (keyAvailable) "GEMINI READY" else "LOCAL ENG SIM",
                                color = if (keyAvailable) NeonGreen else NeonPink,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User info",
                            tint = NeonPurpleGlow,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(DarkSurface)
                                .clickable { viewModel.currentScreen.value = ViralForgeScreen.SETTINGS }
                        )
                    }
                }
            },
            containerColor = DarkBg,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (activeScreen) {
                    ViralForgeScreen.DASHBOARD -> CreatorDashboardView(viewModel)
                    ViralForgeScreen.TITLES -> TitleGeneratorView(viewModel)
                    ViralForgeScreen.THUMBNAILS -> ThumbnailAnalyzerView(viewModel)
                    ViralForgeScreen.HOOKS -> HookGeneratorView(viewModel)
                    ViralForgeScreen.SCRIPTS -> ScriptWriterView(viewModel)
                    ViralForgeScreen.RETENTION -> RetentionAnalyzerView(viewModel)
                    ViralForgeScreen.COMPETITORS -> CompetitorIntelligenceView(viewModel)
                    ViralForgeScreen.TOPICS -> ViralTopicsView(viewModel)
                    ViralForgeScreen.COACH -> GrowthCoachView(viewModel)
                    ViralForgeScreen.SETTINGS -> SettingsView(viewModel)
                    else -> CreatorDashboardView(viewModel)
                }
            }
        }
    }
}

fun getScreenTitle(screen: ViralForgeScreen): String {
    return when (screen) {
        ViralForgeScreen.DASHBOARD -> "Creator Dashboard"
        ViralForgeScreen.TITLES -> "Viral Title Generator"
        ViralForgeScreen.THUMBNAILS -> "Thumbnail Eye Analyzer"
        ViralForgeScreen.HOOKS -> "Retention Hook Synthesizer"
        ViralForgeScreen.SCRIPTS -> "Story-paced Scripter"
        ViralForgeScreen.RETENTION -> "Why My Video Failed?"
        ViralForgeScreen.COMPETITORS -> "Competitor Intel Dashboard"
        ViralForgeScreen.TOPICS -> "Viral Trend Forecasting"
        ViralForgeScreen.COACH -> "Growth Coach Analytics"
        ViralForgeScreen.SETTINGS -> "System Configurations"
        else -> "Workspace"
    }
}

// Sidebar Drawer layout
@Composable
fun SidebarMenuContent(
    viewModel: ViralForgeViewModel,
    activeScreen: ViralForgeScreen,
    onMenuClicked: () -> Unit
) {
    val email by viewModel.userEmail.collectAsStateWithLifecycle()
    val tier by viewModel.userTier.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkSurface)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        // Workspace Brand Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(NeonPurple, NeonPink)),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize().padding(2.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text("ViralForge AI", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Screen Navigation List
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SidebarItem(Icons.Default.Dashboard, "Dashboard", activeScreen == ViralForgeScreen.DASHBOARD) {
                viewModel.currentScreen.value = ViralForgeScreen.DASHBOARD
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Title, "Viral Titles", activeScreen == ViralForgeScreen.TITLES) {
                viewModel.currentScreen.value = ViralForgeScreen.TITLES
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Image, "Thumbnail Eye", activeScreen == ViralForgeScreen.THUMBNAILS) {
                viewModel.currentScreen.value = ViralForgeScreen.THUMBNAILS
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Link, "Hook Synthesizer", activeScreen == ViralForgeScreen.HOOKS) {
                viewModel.currentScreen.value = ViralForgeScreen.HOOKS
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Description, "Story Scripter", activeScreen == ViralForgeScreen.SCRIPTS) {
                viewModel.currentScreen.value = ViralForgeScreen.SCRIPTS
                onMenuClicked()
            }
            SidebarItem(Icons.Default.TrendingUp, "Why Video Failed?", activeScreen == ViralForgeScreen.RETENTION) {
                viewModel.currentScreen.value = ViralForgeScreen.RETENTION
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Group, "Competitor Intel", activeScreen == ViralForgeScreen.COMPETITORS) {
                viewModel.currentScreen.value = ViralForgeScreen.COMPETITORS
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Search, "Viral Trend Ideas", activeScreen == ViralForgeScreen.TOPICS) {
                viewModel.currentScreen.value = ViralForgeScreen.TOPICS
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Star, "Growth Coach", activeScreen == ViralForgeScreen.COACH) {
                viewModel.currentScreen.value = ViralForgeScreen.COACH
                onMenuClicked()
            }
            SidebarItem(Icons.Default.Settings, "Settings", activeScreen == ViralForgeScreen.SETTINGS) {
                viewModel.currentScreen.value = ViralForgeScreen.SETTINGS
                onMenuClicked()
            }
        }

        Divider(color = DarkBorder, modifier = Modifier.padding(vertical = 12.dp))

        // User Account Box at very bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkCard, RoundedCornerShape(12.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(NeonPurple.copy(alpha = 0.2f), CircleShape)
                    .border(1.dp, NeonPurple, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (email.isNotEmpty()) email.take(1).uppercase() else "C",
                    color = NeonPurpleGlow,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (email.isNotEmpty()) email.substringBefore("@") else "Creator Hub",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = tier,
                    fontSize = 10.sp,
                    color = NeonCyan,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(onClick = { viewModel.logout() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Exit", tint = NeonPink)
            }
        }
    }
}

@Composable
fun SidebarItem(icon: ImageVector, text: String, isSelected: Boolean, onClick: () -> Unit) {
    val background = if (isSelected) NeonPurple.copy(alpha = 0.15f) else Color.Transparent
    val borderCol = if (isSelected) NeonPurple.copy(alpha = 0.3f) else Color.Transparent
    val tintColor = if (isSelected) NeonPurpleGlow else TextSecondary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(background, RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, borderCol), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tintColor, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = if (isSelected) TextPrimary else TextSecondary, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}


// ==================================================
// VIEW 1: CREATOR DASHBOARD
// ==================================================
@Composable
fun CreatorDashboardView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var titleQuery by remember { mutableStateFlowOf("") }
    val history by viewModel.historyItems.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Greeting & Coach Insight Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(listOf(DarkSurface, DarkCard)),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("COACH WEEKLY ALERT", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "A/B-split titles with curiosity brackets (e.g. '[Shocking]') yield an immediate 23% early CTR velocity boost. Apply Title Synthesizer to test your concept.",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // Circular Canvas Gauges Row for SaaS metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                DashboardMetricArc("Content Velocity", 91, NeonPurple)
            }
            Box(modifier = Modifier.weight(1f)) {
                DashboardMetricArc("Consistency Score", 85, NeonCyan)
            }
            Box(modifier = Modifier.weight(1f)) {
                DashboardMetricArc("Niche Strength", 94, NeonPink)
            }
        }

        // Analytics Graph Card (Simulated Custom Drawing Canvas)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Session View Velocity", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                        Text("Dynamic channel analytics over the last 30 hours", fontSize = 10.sp, color = TextSecondary)
                    }
                    Text("+44.2%", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Custom canvas draw
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .padding(vertical = 10.dp)
                ) {
                    val width = size.width
                    val height = size.height
                    
                    // Draw grid helper lines
                    for (i in 1..4) {
                        val y = height * (i / 4f)
                        drawLine(
                            color = DarkBorder.copy(alpha = 0.5f),
                            start = Offset(0f, y),
                            end = Offset(width, y)
                        )
                    }

                    // Mock points
                    val points = listOf(20f, 35f, 40f, 32f, 55f, 82f, 75f, 96f, 88f, 100f)
                    val path = Path()
                    val brush = Brush.verticalGradient(
                        colors = listOf(NeonPurple.copy(alpha = 0.4f), Color.Transparent),
                        startY = 0f,
                        endY = height
                    )
                    
                    points.forEachIndexed { idx, pt ->
                        val x = width * (idx / (points.size - 1).toFloat())
                        val y = height - (pt / 100f * height)
                        if (idx == 0) {
                            path.moveTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                        
                        // Draw point spark glow
                        if (idx == points.size - 1) {
                            drawCircle(color = NeonPurpleGlow, radius = 6f, center = Offset(x, y))
                        }
                    }

                    // Stroke Path
                    drawPath(path = path, color = NeonPurple, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
                    
                    // Fill Path gradient
                    path.lineTo(width, height)
                    path.lineTo(0f, height)
                    path.close()
                    drawPath(path = path, brush = brush)
                }
            }
        }

        // Quick Creator Tools Menu Grid
        Text("Active Workspace Modules", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WorkspaceQuickBtn("Titles Synthesizer", Icons.Default.Title, NeonPurple, modifier = Modifier.weight(1f)) {
                viewModel.currentScreen.value = ViralForgeScreen.TITLES
            }
            WorkspaceQuickBtn("Eye heatmaps", Icons.Default.Image, NeonCyan, modifier = Modifier.weight(1f)) {
                viewModel.currentScreen.value = ViralForgeScreen.THUMBNAILS
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WorkspaceQuickBtn("Failed Diagnoses", Icons.Default.TrendingUp, NeonPink, modifier = Modifier.weight(1f)) {
                viewModel.currentScreen.value = ViralForgeScreen.RETENTION
            }
            WorkspaceQuickBtn("Pacing Scripter", Icons.Default.Description, NeonPurpleGlow, modifier = Modifier.weight(1f)) {
                viewModel.currentScreen.value = ViralForgeScreen.SCRIPTS
            }
        }

        // Recent Saved Logs inside Database Room list
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Historical Workspace Saves", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Room DB Local", fontSize = 10.sp, color = NeonCyan)
        }

        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(12.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = TextMuted, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No saved scripts, favorites or logs in database yet.", color = TextSecondary, fontSize = 11.sp)
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                history.take(4).forEach { item ->
                    HistoryRowItem(item, viewModel)
                }
            }
        }
    }
}

@Composable
fun DashboardMetricArc(title: String, percentage: Int, progressColor: Color) {
    Box(
        modifier = Modifier
            .background(DarkSurface, RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // background circle
                    drawCircle(color = DarkBorder, style = Stroke(width = 4.dp.toPx()))
                    // progress sweep arc
                    drawArc(
                        color = progressColor,
                        startAngle = -90f,
                        sweepAngle = percentage * 3.6f,
                        useCenter = false,
                        style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text("$percentage%", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, color = TextSecondary, fontSize = 10.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun WorkspaceQuickBtn(label: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .background(DarkSurface, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = label, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
fun HistoryRowItem(item: CreatorHistoryItem, viewModel: ViralForgeViewModel) {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSurface, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon type tag
        val (icon, tint) = when (item.type) {
            "title" -> Pair(Icons.Default.Title, NeonPurple)
            "hook" -> Pair(Icons.Default.Link, NeonCyan)
            "script" -> Pair(Icons.Default.Description, NeonPurpleGlow)
            "thumbnail" -> Pair(Icons.Default.Image, NeonPink)
            else -> Pair(Icons.Default.Star, NeonPink)
        }
        
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(tint.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.content.take(65) + if (item.content.length > 65) "..." else "",
                fontSize = 11.sp,
                color = TextSecondary
            )
        }

        IconButton(onClick = {
            clipboard.setText(AnnotatedString(item.content))
            Toast.makeText(context, "Copied workspace save to Clipboard", Toast.LENGTH_SHORT).show()
        }) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(16.dp))
        }
        IconButton(onClick = { viewModel.deleteHistoryItem(item) }) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Delete", tint = NeonPink.copy(alpha = 0.6f), modifier = Modifier.size(16.dp))
        }
    }
}


// ==================================================
// VIEW 2: VIRAL TITLE GENERATOR
// ==================================================
@Composable
fun TitleGeneratorView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val niche by viewModel.titleInputNiche.collectAsStateWithLifecycle()
    val topic by viewModel.titleInputTopic.collectAsStateWithLifecycle()
    val emotion by viewModel.titleInputEmotion.collectAsStateWithLifecycle()
    val audience by viewModel.titleInputAudience.collectAsStateWithLifecycle()
    val isGenerating by viewModel.titleGenerating.collectAsStateWithLifecycle()
    val results by viewModel.generatedTitles.collectAsStateWithLifecycle()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Controls card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Synthesizer Constraints", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = niche,
                    onValueChange = { viewModel.titleInputNiche.value = it },
                    label = { Text("Channel Niche") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = topic,
                    onValueChange = { viewModel.titleInputTopic.value = it },
                    label = { Text("Core Video Topic") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = emotion,
                            onValueChange = { viewModel.titleInputEmotion.value = it },
                            label = { Text("Core Emotion") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                                focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = audience,
                            onValueChange = { viewModel.titleInputAudience.value = it },
                            label = { Text("Target Audience") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                                focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { viewModel.generateTitles() },
                    enabled = !isGenerating,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Synthesizing Titles...", color = Color.White)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.FlashOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate 4 Viral Titles", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Title List outputs
        if (results.isNotEmpty()) {
            Text("AI Synthesized Variations", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            
            results.forEach { result ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkCard, RoundedCornerShape(16.dp))
                        .border(BorderStroke(1.dp, NeonPurple.copy(alpha = 0.25f)), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = result.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            lineHeight = 22.sp
                        )

                        // Scoring Chips
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ScoreChip("Predicted CTR", result.ctrScore, NeonPurple)
                            ScoreChip("Curiosity Gap", result.curiosityScore, NeonCyan)
                            ScoreChip("Emotional Friction", result.emotionalScore, NeonPink)
                        }

                        Divider(color = DarkBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Trigger: ${result.trigger}",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row {
                                IconButton(onClick = {
                                    clipboard.setText(AnnotatedString(result.title))
                                    Toast.makeText(context, "Title copied to Clipboard", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Copy", tint = TextSecondary, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreChip(label: String, score: Int, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, color.copy(alpha = 0.3f)), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$score%", color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, color = TextSecondary, fontSize = 9.sp)
        }
    }
}


// ==================================================
// VIEW 3: THUMBNAIL EYE ANALYZER
// ==================================================
@Composable
fun ThumbnailAnalyzerView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val ratings by viewModel.thumbnailRating.collectAsStateWithLifecycle()
    val readability by viewModel.thumbnailReadability.collectAsStateWithLifecycle()
    val contrast by viewModel.thumbnailContrast.collectAsStateWithLifecycle()
    val clutter by viewModel.thumbnailClutter.collectAsStateWithLifecycle()
    val faceEmotion by viewModel.thumbnailFacial.collectAsStateWithLifecycle()
    val predictedCtr by viewModel.thumbnailCtrPrediction.collectAsStateWithLifecycle()
    val indexSuggs by viewModel.thumbnailSuggestions.collectAsStateWithLifecycle()
    val isAnalyzing by viewModel.isAnalyzingThumbnail.collectAsStateWithLifecycle()
    val mockImageId by viewModel.mockSelectedThumbnailRes.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Thumbnail upload triggers Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Select Thumbnail to Analyze", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ThumbnailDraftCard("Asset Preview Beast style", "Beast Surprise", mockImageId == "beast", modifier = Modifier.weight(1f)) {
                        viewModel.analyzeThumbnail("beast")
                    }
                    ThumbnailDraftCard("Asset Preview Dark style", "Cinematic Dark Grid", mockImageId == "dark", modifier = Modifier.weight(1f)) {
                        viewModel.analyzeThumbnail("dark")
                    }
                    ThumbnailDraftCard("Asset Preview Finance style", "Gold Finance Contrast", mockImageId == "finance", modifier = Modifier.weight(1f)) {
                        viewModel.analyzeThumbnail("finance")
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (isAnalyzing) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = NeonCyan, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Analyzing eye tracking heatmap Simulation...", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Analysis Results Card
        if (mockImageId.isNotEmpty() && !isAnalyzing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(16.dp))
                    .border(BorderStroke(1.dp, RoyalBorder(mockImageId)), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    // Rating Sweep Arc Gauge + predicted key status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rating gauge programmatic drawing
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawCircle(color = DarkBorder, style = Stroke(width = 6.dp.toPx()))
                                drawArc(
                                    color = NeonCyan,
                                    startAngle = -90f,
                                    sweepAngle = ratings * 3.6f,
                                    useCenter = false,
                                    style = Stroke(width = 7.dp.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$ratings", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("/100", color = TextMuted, fontSize = 9.sp)
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text("Thumbnail Health Score", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                            Text("Predicted CTR Index: $predictedCtr", fontSize = 12.sp, color = NeonCyan, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Divider(color = DarkBorder)

                    // Diagnostic Metrics List
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DiagnosticMetricRow("Readability Index", readability, NeonCyan)
                        DiagnosticMetricRow("Contrast Dynamic", contrast, NeonGreen)
                        DiagnosticMetricRow("Asset Clutter Scale", clutter, NeonPink)
                        DiagnosticMetricRow("Facial Expression Hook", faceEmotion, NeonPurpleGlow)
                    }

                    Divider(color = DarkBorder)

                    // Redesign Suggestions
                    Text("Creator Layout Optimization Suggest", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        indexSuggs.forEachIndexed { index, suggestion ->
                            Row {
                                Text("${index + 1}. ", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(suggestion, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RoyalBorder(mockImageId: String): Color {
    return when (mockImageId) {
        "beast" -> NeonPink
        "dark" -> NeonPurple
        else -> NeonCyan
    }
}

@Composable
fun DiagnosticMetricRow(label: String, valText: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = TextSecondary, fontSize = 12.sp)
        Box(
            modifier = Modifier
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(text = valText, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ThumbnailDraftCard(label: String, styleTxt: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(DarkCard, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.5.dp, if (isSelected) NeonCyan else DarkBorder), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(DarkBg, RoundedCornerShape(8.dp))
                    .border(BorderStroke(1.dp, if (isSelected) NeonCyan.copy(alpha = 0.5f) else DarkBorder), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    tint = if (isSelected) NeonCyan else TextMuted,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = styleTxt, color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}


// ==================================================
// VIEW 4: HOOK GENERATOR VIEW
// ==================================================
@Composable
fun HookGeneratorView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val topic by viewModel.hookTopic.collectAsStateWithLifecycle()
    val isGenerating by viewModel.hookGenerating.collectAsStateWithLifecycle()
    val results by viewModel.generatedHooks.collectAsStateWithLifecycle()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Synthesize Psychology Hooks", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = topic,
                    onValueChange = { viewModel.hookTopic.value = it },
                    label = { Text("Core Script Idea / Topic") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.generateHooks() },
                    enabled = !isGenerating,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Forging neuro-hooks...", color = Color.White)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate Psychology Hooks", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Output Display
        if (results.isNotEmpty() && !isGenerating) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(16.dp))
                    .border(BorderStroke(1.dp, NeonCyan.copy(alpha = 0.3f)), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Forged Narrative Hooks", fontWeight = FontWeight.Bold, color = NeonCyan, fontSize = 14.sp)
                        IconButton(onClick = {
                            clipboard.setText(AnnotatedString(results))
                            Toast.makeText(context, "Hooks copied to Clipboard", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Copy all", tint = TextPrimary)
                        }
                    }
                    Divider(color = DarkBorder)
                    Text(
                        text = results,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


// ==================================================
// VIEW 5: SCRIPTWRITER
// ==================================================
@Composable
fun ScriptWriterView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val topic by viewModel.scriptTopic.collectAsStateWithLifecycle()
    val type by viewModel.scriptType.collectAsStateWithLifecycle()
    val isGenerating by viewModel.scriptGenerating.collectAsStateWithLifecycle()
    val results by viewModel.generatedScript.collectAsStateWithLifecycle()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("High-Retention Story scripter", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = topic,
                    onValueChange = { viewModel.scriptTopic.value = it },
                    label = { Text("Script Core Subject") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Fast length choice
                Text("Script Type Profile", color = TextSecondary, fontSize = 12.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ScriptTypeBtn("Shorts (90s)", type == "Shorts (90s)", modifier = Modifier.weight(1f)) {
                        viewModel.scriptType.value = "Shorts (90s)"
                    }
                    ScriptTypeBtn("Long (10m)", type == "Long (10m)", modifier = Modifier.weight(1f)) {
                        viewModel.scriptType.value = "Long (10m)"
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { viewModel.generateScript() },
                    enabled = !isGenerating,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Writing script layers...", color = Color.White)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate Script Layout", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Script output
        if (results.isNotEmpty() && !isGenerating) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(16.dp))
                    .border(BorderStroke(1.dp, NeonPurpleGlow.copy(alpha = 0.3f)), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Forged YouTube Script", fontWeight = FontWeight.Bold, color = NeonPurpleGlow, fontSize = 14.sp)
                        IconButton(onClick = {
                            clipboard.setText(AnnotatedString(results))
                            Toast.makeText(context, "Script copied to Clipboard", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Copy text", tint = TextPrimary)
                        }
                    }
                    Divider(color = DarkBorder)
                    Text(
                        text = results,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun ScriptTypeBtn(label: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(DarkCard, RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, if (isSelected) NeonPurple else DarkBorder), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = if (isSelected) TextPrimary else TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}


// ==================================================
// VIEW 6: RETENTION ANALYZER (Why Video Failed?)
// ==================================================
@Composable
fun RetentionAnalyzerView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val urlInput by viewModel.videoUrlInput.collectAsStateWithLifecycle()
    val isAnalyzing by viewModel.isAnalyzingRetention.collectAsStateWithLifecycle()
    val pacingScore by viewModel.retentionPacingScore.collectAsStateWithLifecycle()
    val lowEnergyZones by viewModel.retentionLowEnergyZones.collectAsStateWithLifecycle()
    val suggestions by viewModel.retentionSuggestions.collectAsStateWithLifecycle()
    val points by viewModel.retentionHeatmapTimeline.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Analyze Failed Videos", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = urlInput,
                    onValueChange = { viewModel.videoUrlInput.value = it },
                    label = { Text("Enter YouTube Raw Video URL") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.analyzeVideoUrl() },
                    enabled = !isAnalyzing,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Deconstructing video streams...", color = Color.White)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Diagnose Failed Retentions", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Analyzed telemetries results inside custom Canvas curves
        if (!isAnalyzing && pacingScore != "Highly Optimized") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(16.dp))
                    .border(BorderStroke(1.dp, NeonPink.copy(alpha = 0.3f)), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Video Diagnostic Retention Report", fontWeight = FontWeight.Bold, color = NeonPink, fontSize = 14.sp)
                    
                    Text("Simulated Audience Attention Curve", color = TextSecondary, fontSize = 11.sp)

                    // Draw the line chart beautifully in canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(DarkBg, RoundedCornerShape(8.dp))
                            .border(BorderStroke(0.5.dp, DarkBorder), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        val width = size.width
                        val height = size.height
                        val path = Path()
                        
                        points.forEachIndexed { idx, pt ->
                            val x = width * (idx / (points.size - 1).toFloat())
                            val y = height - (pt / 100f * height)
                            if (idx == 0) {
                                path.moveTo(x, y)
                            } else {
                                path.lineTo(x, y)
                            }
                            
                            // Draw attention drops color highlights
                            if (pt < 65f) {
                                drawCircle(color = NeonPink, radius = 5f, center = Offset(x, y))
                            }
                        }

                        drawPath(path = path, color = NeonPink, style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round))
                    }

                    Divider(color = DarkBorder)

                    Text("Retention Diagnostics", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FlowReportItem("Narrative Pacing Flow Score", pacingScore, NeonPink)
                        FlowReportItem("Critical Retention Drop Zones", lowEnergyZones, NeonPink)
                        FlowReportItem("Actionable Editor Corrections", suggestions, NeonCyan)
                    }
                }
            }
        }
    }
}

@Composable
fun FlowReportItem(label: String, desc: String, color: Color) {
    Column {
        Text(text = label, color = TextSecondary, fontSize = 11.sp)
        Text(text = desc, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 2.dp))
    }
}


// ==================================================
// VIEW 7: COMPETITOR INTELLIGENCE VIEW
// ==================================================
@Composable
fun CompetitorIntelligenceView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val competitorUrl by viewModel.competitorUrlInput.collectAsStateWithLifecycle()
    val isAnalyzing by viewModel.isAnalyzingCompetitor.collectAsStateWithLifecycle()
    val chanName by viewModel.competitorChannelName.collectAsStateWithLifecycle()
    val growthScore by viewModel.competitorGrowthScore.collectAsStateWithLifecycle()
    val formula by viewModel.competitorViralFormula.collectAsStateWithLifecycle()
    val bestTopics by viewModel.competitorBestTopics.collectAsStateWithLifecycle()
    val insights by viewModel.competitorInsights.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Deconstruct Competitor Formula", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = competitorUrl,
                    onValueChange = { viewModel.competitorUrlInput.value = it },
                    label = { Text("Competitor Channel Handle") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.analyzeCompetitorChannel() },
                    enabled = !isGenerating(isAnalyzing),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Deconstructing channel formula...", color = Color.White)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Group, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Analyze Channel Formula", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Competitor breakdown cards
        if (!isAnalyzing && formula.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(16.dp))
                    .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = chanName, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 16.sp)
                        Box(
                            modifier = Modifier
                                .background(NeonCyan.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = growthScore, color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    Divider(color = DarkBorder)

                    Text("Viral Layout Psychology Formula", fontWeight = FontWeight.Bold, color = NeonPurpleGlow, fontSize = 12.sp)
                    Text(text = formula, color = TextSecondary, fontSize = 12.sp, lineHeight = 18.sp)

                    Divider(color = DarkBorder)

                    Text("Best Performing Topics List", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        bestTopics.forEach { topic ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(6.dp).background(NeonCyan, CircleShape))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = topic, color = TextPrimary, fontSize = 12.sp)
                            }
                        }
                    }

                    Divider(color = DarkBorder)

                    Text("Strategic Channel Insights", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        insights.forEach { insight ->
                            Row {
                                Text("•", color = NeonPurple, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = insight, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun isGenerating(b: Boolean): Boolean {
    return b
}


// ==================================================
// VIEW 8: VIRAL TRENDING TOPICS VIEW
// ==================================================
@Composable
fun ViralTopicsView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()
    val nicheInput by viewModel.topicNicheInput.collectAsStateWithLifecycle()
    val isFinding by viewModel.isFindingTopics.collectAsStateWithLifecycle()
    val results by viewModel.viralTopicsResults.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Viral Topic Forecasting Idea", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = nicheInput,
                    onValueChange = { viewModel.topicNicheInput.value = it },
                    label = { Text("Video Niche Category") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = NeonPurple, unfocusedBorderColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.findViralTopics() },
                    enabled = !isFinding,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isFinding) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Searching trend database...", color = Color.White)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Find Low Competition Trends", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Topics forecast rows
        if (!isFinding && results.isNotEmpty()) {
            Text("Trending low competition possibilities", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            
            results.forEach { res ->
                val topicName = res["topic"] as? String ?: ""
                val trend = res["trend"] as? Int ?: 80
                val competition = res["competition"] as? String ?: "Low"
                val virality = res["virality"] as? String ?: "High"

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkCard, RoundedCornerShape(14.dp))
                        .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = topicName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary, lineHeight = 18.sp)
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .background(NeonPurple.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text("Trend Index: $trend%", color = NeonPurpleGlow, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .background(NeonGreen.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text("Competition: $competition", color = NeonGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .background(NeonPink.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text("Virality: $virality", color = NeonPink, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}


// ==================================================
// VIEW 9: GROWTH COACH VIEW
// ==================================================
@Composable
fun GrowthCoachView(viewModel: ViralForgeViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Coach Introduction
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(listOf(DarkSurface, DarkCard)),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(NeonPurple.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = NeonPurpleGlow, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("AI Growth Coach & Optimizer", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Actionable instructions based on thousands of high performing YouTube viral mechanics.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Daily Activity Tracker Form
        Text("Daily Retention Checklist", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CoachCheckItem("Deconstruct dynamic thumbnail readability on Compact grids (4.7 in)", true)
            CoachCheckItem("Align Title emotion triggers directly with viewer loss aversion factor", true)
            CoachCheckItem("Verify the immediate 5-second storytelling cliffhanger script parameters", false)
            CoachCheckItem("Prune stagnant graphic pauses down to 1.8 second transition thresholds", false)
        }

        // Historical coaching archives
        Text("Analytical Creator Blueprint rules", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        viewModel.weeklyGrowthTips.forEachIndexed { idx, tip ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkCard, RoundedCornerShape(12.dp))
                    .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Row {
                    Text(text = "0${idx + 1}", color = NeonCyan, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(text = tip, color = TextPrimary, fontSize = 12.sp, lineHeight = 18.sp)
                }
            }
        }
    }
}

@Composable
fun CoachCheckItem(text: String, initiallyChecked: Boolean) {
    var checked by remember { mutableStateOf(initiallyChecked) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSurface, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, if (checked) NeonPurple.copy(alpha = 0.3f) else DarkBorder), RoundedCornerShape(12.dp))
            .clickable { checked = !checked }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (checked) Icons.Default.Check else Icons.Default.Star,
            contentDescription = null,
            tint = if (checked) NeonPurpleGlow else TextMuted,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = if (checked) TextPrimary else TextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}


// ==================================================
// VIEW 10: SETTINGS VIEW (API Key Warnings info)
// ==================================================
@Composable
fun SettingsView(viewModel: ViralForgeViewModel) {
    val email by viewModel.userEmail.collectAsStateWithLifecycle()
    val tier by viewModel.userTier.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface, RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, DarkBorder), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Developer Configuration Specs", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 15.sp)
                
                DetailSettingItem("User Identity", email.ifEmpty { "SPIKE2DANDEKAR@gmail.com" })
                DetailSettingItem("SaaS Package Tier", tier)
                DetailSettingItem("System Engine Status", if (GeminiService.isKeyAvailable()) "Connected REST Gemini V1beta" else "Simulated Creator Intelligence Local Heuristics")
                
                Divider(color = DarkBorder, modifier = Modifier.padding(vertical = 4.dp))

                Text("Secret API Keys Warnings", fontWeight = FontWeight.Bold, color = NeonPink, fontSize = 13.sp)
                Text(
                    text = "Security Warning: I have included your API keys in the generated APK file for this prototype. Please be aware that Android APKs can be easily decompiled, and these keys can be extracted by anyone who has access to the file. Do not share this APK file publicly or with unauthorized individuals to prevent potential misuse.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.logout() },
            colors = ButtonDefaults.buttonColors(containerColor = NeonPink.copy(alpha = 0.2f)),
            border = BorderStroke(1.dp, NeonPink.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Disconnect From Workspace", color = NeonPink, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DetailSettingItem(label: String, valText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = TextSecondary, fontSize = 12.sp)
        Text(text = valText, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
